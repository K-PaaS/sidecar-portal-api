package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.routes.RouteRelationships;
import org.cloudfoundry.reactor.TokenProvider;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.model.Route;
import org.kpaas.sidecar.portal.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
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

    String appGuid;
    String routeGuid;
    String packageGuid;
    String buildGuid;
    String dropletGuid;

    @RequestMapping(value = Constants.URI_SIDECAR_API_PREFIX + "/app/push", method = RequestMethod.POST)
    public void push(@RequestParam MultipartFile multipartFile) throws Exception {
        /*
        String name = stringNullCheck(requestData.get("name"));
        String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));
        if ( name.isEmpty() || spaceGuid.isEmpty() ){ // 차후 수정

            throw new NullPointerException("NULL 발생");
        }
        */
        String name = "pushtest";
        String spaceGuid = "cf-space-36ab0f6a-7cb4-4f4d-aa88-382a9dc466cb";


        Application app = new Application();
        app.setName(name);
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());

        // 1. JAVA 내부에서 사용할 임시 파일을 생성 - 생략
        //String host = stringNullCheck(requestData.get("host"));
        //String domainGuid = stringNullCheck(requestData.get("domainGuid"));
        //String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));

        //if ( host.isEmpty() || domainGuid.isEmpty() ){ // 차후 수정
        //    throw new NullPointerException("NULL 발생");
        //}
        String host = "pushtest";
        String domainGuid = "default-domain";

        Route route = new Route();
        route.setHost(host);
        route.setRelationships(RouteRelationships.builder()
                .domain(ToOneRelationship.builder().data(Relationship.builder().id(domainGuid).build()).build())
                .space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build())
                .build());
        // 2. App 생성
        //Application app = null;
        appGuid = appServiceV3.create(app).getId();

        // 3. ROUTE 생성
        //Route route = null;
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
                            //dropletGuid = buildsService.get(buildGuid, tokenProvider).getDroplet().getId();

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

    @ApiOperation(value = "Sidecar Upload File", nickname = "minioUploadFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "request parameters", required = true, dataType = "common.model.Params", paramType = "body"
            )})
    //, consumes = "multipart/form-data; boundary=luna_test")
    @RequestMapping(value = Constants.URI_SIDECAR_API_PREFIX + "/miniofile", method = RequestMethod.POST, produces = "application/json")
    public Object minioUploadFile(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return sidecarService.minioUploadFile(file);
    }

    @RequestMapping(value = Constants.URI_SIDECAR_API_PREFIX + "/miniofile", method = RequestMethod.GET)
    public Object minioDownloadFile() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return sidecarService.minioDownloadFile();
    }
}
