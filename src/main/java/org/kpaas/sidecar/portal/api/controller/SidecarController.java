package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.*;
import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.routes.RouteRelationships;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsResponse;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingResource;
import org.cloudfoundry.reactor.TokenProvider;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.model.ApplicationService;
import org.kpaas.sidecar.portal.api.model.Process;
import org.kpaas.sidecar.portal.api.model.Route;
import org.kpaas.sidecar.portal.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.Base64;
import java.util.Base64.Decoder;

@RestController
public class SidecarController extends Common {

    // build process interval time (sec)
    private final long BUILD_INTERVAL_SECOND = 300;

    /*
    * 앱 배포 과정
    1. JAVA 내부에서 사용할 임시 파일을 생성
    2. APP 생성
    3. ROUTE 생성
    4. APP - ROUTE 맵핑
    5. 패키지 생성
    6. 패키지에 임시 파일을 업로드
    7. 빌드 생성
    8. 앱 시작
    */

    @Autowired
    private ApplicationsServiceV3 appServiceV3;

    @Autowired
    private RoutesServiceV3 routesServiceV3;

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private BuildsService buildsService;

    @Autowired
    private DropletsService dropletsService;

    @Autowired
    private SidecarService sidecarService;

    @Autowired
    private ProcessesService processesService;

    @Autowired
    private ServiceBindingsServiceV3 serviceBindingsServiceV3;

    @Autowired
    private ServiceInstancesServiceV3 serviceInstancesServiceV3;
    String appGuid;
    String routeGuid;
    String packageGuid;
    String buildGuid;
    String dropletGuid;


    @RequestMapping(value = Constants.URI_SIDECAR_API_PREFIX + "/app/push", method = RequestMethod.POST, headers = ("content-type=multipart/form-data"))
    public void push(@RequestPart @ApiParam(hidden = true) Map<String, String> requestData, @RequestPart(value = "multipartFile",required = true) MultipartFile multipartFile) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));
        if ( name.isEmpty() || spaceGuid.isEmpty() ){ // 차후 수정

            throw new NullPointerException("NULL 발생");
        }

        String memory = stringNullCheck(requestData.get("memory"));
        String disk = stringNullCheck(requestData.get("disk"));
        if ( memory.isEmpty() || disk.isEmpty() ){ // 차후 수정

            throw new NullPointerException("NULL 발생");
        }

        Application app = new Application();
        app.setName(name);
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());

        // 1. JAVA 내부에서 사용할 임시 파일을 생성 - 생략
        String host = stringNullCheck(requestData.get("host"));
        String domainGuid = stringNullCheck(requestData.get("domainGuid"));

        if ( host.isEmpty() || domainGuid.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }

        Route route = new Route();
        route.setHost(host);
        route.setRelationships(RouteRelationships.builder()
                .domain(ToOneRelationship.builder().data(Relationship.builder().id(domainGuid).build()).build())
                .space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build())
                .build());
        // 2. App 생성
        appGuid = appServiceV3.create(app).getId();

        // 3. ROUTE 생성
        routeGuid = routesServiceV3.create(route).getId();

        // 4. APP - ROUTE 맵핑
        routesServiceV3.insertDestinations(routeGuid, appGuid);

        // 5. 패키지 생성
        packageGuid = packagesService.create(appGuid).getId();

        // 6. 패키지에 임시 파일을 업로드
        packagesService.upload(packageGuid, multipartFile);

        TokenProvider tokenProvider = tokenProvider(authUtil.sidecarAuth().getClusterToken());
        // 7. 빌드 생성
        Thread th = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CreateBuildResponse createBuildResponse = buildsService.create(packageGuid, tokenProvider);
                            buildGuid = createBuildResponse.getId();

                            //현재 시각
                            long start = System.currentTimeMillis();

                            //종료 시각
                            long end = start + BUILD_INTERVAL_SECOND *1000;

                            // 빌드 확인 중 = STAGED
                            while(true){
                                if( buildsService.get(buildGuid, tokenProvider).getState().getValue().equals("STAGED") ) {
                                    break;
                                }
                                if ( System.currentTimeMillis() > end ){
                                    throw new Exception("App Build Time Over");
                                }
                                Thread.sleep(1000);
                            }

                            dropletGuid = buildsService.get(buildGuid, tokenProvider).getDroplet().getId();
                            appServiceV3.setCurrentDroplet(appGuid, dropletGuid, tokenProvider);
                            //앱 실행버튼이 on일때
                            if (true) {
                                // 8. 앱 시작
                                Process process = new Process();
                                process.setDiskInMb(Integer.valueOf(disk));
                                process.setMemoryInMb(Integer.valueOf(memory));
                                process.setInstances(null);
                                processesService.scale("cf-proc-" + appGuid + "-web", process, tokenProvider);
                                appServiceV3.start(appGuid, tokenProvider);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
        );
        th.start();
    }

    private final SidecarRestTemplateService restTemplateService;

    public SidecarController(SidecarRestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    @RequestMapping(value = Constants.URI_SIDECAR_API_PREFIX + "/apps/customList", method = RequestMethod.GET)
    public List<Application> customList(@RequestParam(required = false) @ApiParam(value = "Space GUIDs", required = true)String spaceGuid) throws Exception {

        String appGuid;
        String name;
        int currentInstance;
        int maxInstance = 0;
        Integer memory = null;
        Integer disk = null;
        List<String> urls = new ArrayList<>();
        String processGuid;
        String status;

        List<Application> applications = new ArrayList<>();
        Application application;

        List<Map> maps = new ArrayList<>();
        List<Map> apps = new ArrayList<>();
        List<Map> processes = new ArrayList<>();
        List<Map> routes = new ArrayList<>();
        String reqUrl = "/apis/korifi.cloudfoundry.org/v1alpha1/namespaces/"+ spaceGuid + "/cfapps";
        Map map;
        map = restTemplateService.send(org.container.platform.api.common.Constants.TARGET_CP_MASTER_API, reqUrl, HttpMethod.GET, null, Map.class, authUtil.sidecarAuth());

        apps = (List<Map>)map.get("items");
        if (apps.size() == 0){
            return applications;
        }
        System.out.println((apps.get(0).get("metadata")));
        Map map2 = (Map) apps.get(0).get("metadata");
        System.out.println(map2.get("name"));
        maps.add(map);

        reqUrl = "/apis/korifi.cloudfoundry.org/v1alpha1/namespaces/"+ spaceGuid + "/cfprocesses?labelSelector=korifi.cloudfoundry.org/process-type=web&limit=500";
        map = restTemplateService.send(org.container.platform.api.common.Constants.TARGET_CP_MASTER_API, reqUrl, HttpMethod.GET, null, Map.class, authUtil.sidecarAuth());
        processes = (List<Map>)map.get("items");
        maps.add(map);

        reqUrl = "/apis/korifi.cloudfoundry.org/v1alpha1/namespaces/"+ spaceGuid + "/cfroutes";
        map = restTemplateService.send(org.container.platform.api.common.Constants.TARGET_CP_MASTER_API, reqUrl, HttpMethod.GET, null, Map.class, authUtil.sidecarAuth());
        routes = (List<Map>)map.get("items");
        maps.add(map);




        for(Map map1 : apps ){
            application = new Application();
            name = (String) ((Map) map1.get("spec")).get("displayName");
            appGuid = (String) ((Map) map1.get("metadata")).get("name");
            status = (String) ((Map) map1.get("spec")).get("desiredState");
            for(Map map3 : processes ){
                if ( ((Map) ((Map) map3.get("metadata")).get("labels")).get("korifi.cloudfoundry.org/app-guid").equals(appGuid) ){
                    memory = ((Integer) ((Map) map3.get("spec")).get("memoryMB"));
                    disk = ((Integer) ((Map) map3.get("spec")).get("diskQuotaMB"));
                    maxInstance = ((Integer) ((Map) map3.get("spec")).get("desiredInstances"));
                    break;
                }
            }


            urls = new ArrayList<>();
            for(Map map3 : routes ) {
                String route_appGuid;
                if((ArrayList) ((Map) map3.get("spec")).get("destinations") == null){
                    continue;
                }
                ArrayList mapss = (ArrayList) ((Map) map3.get("spec")).get("destinations");
                route_appGuid = (String) (((Map)((Map) mapss.get(0)).get("appRef")).get("name"));
                if (route_appGuid.equals(appGuid)) {
                    urls.add(((String) ((Map) map3.get("status")).get("uri")));
                }
            }
            application.setName(name);
            application.setId(appGuid);
            application.setMemoryInMb(memory);
            application.setDiskInMb(disk);
            application.setMaxInstances(maxInstance);
            application.setUrls(urls);
            application.setState(ApplicationState.valueOf(status));
            applications.add(application);
        }
        return applications;
    }



    // App - Binging - Service 묶음
    // Service(GET), Credentials(GET)이 cf-java 및 korifi에서 동작하지 않으므로 k8s 리소스 가져와서 사용
    @RequestMapping(value = Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/bingingServiceList", method = RequestMethod.GET)
    public List<ApplicationService> applicationBingingService(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        List<ApplicationService> applicationServiceList = new ArrayList<>();

        ListServiceBindingsResponse listServiceBindingsResponse = serviceBindingsServiceV3.list(Collections.singletonList(appGuid), null, null, null, null,null);
        ApplicationService applicationService;
        Map service;
        for(ServiceBindingResource serviceBindingResource : listServiceBindingsResponse.getResources() ){
            String serviceGuid = serviceBindingResource.getRelationships().getServiceInstance().getData().getId();

            String reqUrl = "/apis/korifi.cloudfoundry.org/v1alpha1/cfserviceinstances?fieldSelector=metadata.name=" + serviceGuid;
            service = (Map) ((List<Map>) restTemplateService.send(org.container.platform.api.common.Constants.TARGET_CP_MASTER_API, reqUrl, HttpMethod.GET, null, Map.class, authUtil.sidecarAuth()).get("items")).get(0);
            //apps = (List<Map>)map.get("items");
            //appGuid = (String) ((Map) service.get("metadata")).get("name");
            Map<String, Object> credentials = new HashMap<>();
            applicationService = new ApplicationService(serviceBindingResource.getId(), serviceGuid, (String) ((Map) service.get("metadata")).get("name"), credentials);
            applicationServiceList.add(applicationService);
        }

        return applicationServiceList;
    }



    @ApiOperation(value = "Application 환경변수 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "environmentVariables", value = "Application 환경변수 (key, value)", required = true, paramType = "body", dataType = "string")
    })
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/deleteEnvironmentVariables"})
    public Map updateEnvironmentVariables(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData, @PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        String key = String.valueOf(requestData.get("key"));
        System.out.println(key);
        // null 체크 필요
        //배열 변수 넘어오는거 체크 필요
        Map map = new HashMap<>();
        map.put(key, null);
        Application app = new Application();
        Map.Entry<String, ? extends String> environmentVariables;
        environmentVariables = new AbstractMap.SimpleEntry<>(key, null);
        //environmentVariables = new AbstractMap.SimpleEntry<>("test7", null);
        app.setEnvironmentVariables(environmentVariables);
        Map map1 = new HashMap();
        map1.put(key, null);
        Map map2 = new HashMap();
        map2.put("var", map1);
        String reqUrl = "/v3/apps/"+ appGuid + "/environment_variables";
        Map logmap = restTemplateService.send(Constants.TARGET_SIDECAR_API, reqUrl, HttpMethod.PATCH, map2, Map.class, authUtil.sidecarAuth());
        System.out.println(logmap);
        return logmap;
    }

    @ApiOperation(value = "ServiceInstance Credentail 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances/{serviceInstanceGuid}/getCredential"})
    public LinkedHashMap getServiceCredential(@RequestParam(required = true) @ApiParam(value = "Space GUIDs", required = true)String spaceGuid, @PathVariable @ApiParam(value = "ServiceInstance GUID", required = true)String serviceInstanceGuid) throws Exception {
        LinkedHashMap<String, String> services;
        String reqUrl = "/api/v1/namespaces/"+ spaceGuid + "/secrets/"+ serviceInstanceGuid;
        Decoder decoder = Base64.getDecoder();
        services = (LinkedHashMap) restTemplateService.send(org.container.platform.api.common.Constants.TARGET_CP_MASTER_API, reqUrl, HttpMethod.GET, null, Map.class, authUtil.sidecarAuth()).get("data");
        String decodeValue;
        for(String key : services.keySet()) {
            decodeValue = new String (decoder.decode(services.get(key)));
            services.put(key, decodeValue);
        }
        return services;
    }
}
