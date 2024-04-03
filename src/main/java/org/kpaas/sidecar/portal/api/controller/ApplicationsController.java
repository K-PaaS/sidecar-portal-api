package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.*;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.service.ApplicationsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@Api("ApplicationsController v1")
@RestController
public class ApplicationsController extends Common {

    @Autowired
    private ApplicationsServiceV3 appServiceV3;

    /*
     * 앱 생성
     * @RequestBody Application app(name, relationships(space(data(guid)))
     */

    /*@ApiOperation(value = "Application 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "어플리케이션 이름", required = true, paramType = "app - String"),
            @ApiImplicitParam(name = "relationships.space.data.guid", value = "어플리케이션 Space Guid", required = true, paramType = "app - String")
    })
    @PostMapping(value = {"/apps"})
    public CreateApplicationResponse create(@ApiParam(name="app",value="어플리케이션 Body",type = "어플리케이션") @RequestBody Application app) throws Exception {
        return appServiceV3.create(app);
    }*/

    @ApiOperation(value = "Application 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Application 이름", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "spaceGuid", value = "Application Space GUID", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps"})
    public CreateApplicationResponse create(@RequestBody @ApiParam(hidden = true) Map<String, String> requestData) throws Exception  {
        String name = stringNullCheck(requestData.get("name"));
        String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));
        if ( name.isEmpty() || spaceGuid.isEmpty() ){ // 차후 수정

            throw new NullPointerException("NULL 발생");
        }
        Application app = new Application();
        app.setName(name);
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        return appServiceV3.create(app);
    }

    /*
     * 앱 삭제
     */
    @ApiOperation(value = "Application 삭제")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}"})
    public String delete(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.delete(appGuid);
    }

    /*
     * 앱 현재 Droplet 조회
     */
    @ApiOperation(value = "Application 현재 Droplet 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/getCurrentDroplet"})
    public GetApplicationCurrentDropletResponse getCurrentDroplet(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.getCurrentDroplet(appGuid);
    }

    /*
     * 앱 환경변수 조회
     */
    @ApiOperation(value = "Application 환경변수 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/getEnvironment"})
    public GetApplicationEnvironmentResponse getEnvironment(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.getEnvironment(appGuid);
    }

    /*
     * 앱 프로세스 조회
     */
    @ApiOperation(value = "Application 프로세스 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/getProcess"})
    public GetApplicationProcessResponse getProcess(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.getProcess(appGuid);
    }

    /*
     * 앱 SSH 가능 조회
     */
    @ApiOperation(value = "Application SSH 가능 여부 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/getSshEnabled"})
    public GetApplicationSshEnabledResponse getSshEnabled(@PathVariable @ApiParam(value = "Application GUID", required = true) String appGuid) throws Exception {
        return appServiceV3.getSshEnabled(appGuid);
    }

    /*
     * 앱 리스트 조회
     */
    @ApiOperation(value = "Application 리스트 조회")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "names", value = "어플리케이션 GUID", required = false, paramType = "query", dataType = "array[string]"),
            @ApiImplicitParam(name = "orgGuids", value = "어플리케이션 GUID", required = false, paramType = "query", dataType = "array[string]"),
            @ApiImplicitParam(name = "spaceGuids", value = "어플리케이션 GUID", required = false, paramType = "query", dataType = "array[string]")
    })*/
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/list"})
    public ListApplicationsResponse list(@RequestParam(required = false) @ApiParam(value = "Application 이름들")List<String> names, @RequestParam(required = false) @ApiParam(value = "Org GUIDs")List<String> orgGuids, @RequestParam(required = false) @ApiParam(value = "Space GUIDs")List<String> spaceGuids) throws Exception {
        return appServiceV3.list(names, orgGuids, spaceGuids);
    }

    /*
     * 앱 프로세스 리스트 조회
     * @param String appGuid
     */
    @ApiOperation(value = "Application Process 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/listProcesses"})
    public ListApplicationProcessesResponse listProcesses(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid, @RequestParam(required = false)@ApiParam(value = "Process GUIDs", required = false)List<String> processGuids) throws Exception {
        return appServiceV3.listProcesses(appGuid, processGuids);
    }

    /*
     * 앱 라우트 리스트 조회
     * @param String appGuid
     */
    @ApiOperation(value = "Application Route 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/listRoutes"})
    public ListApplicationRoutesResponse listRoutes(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid, @RequestParam(required = false) @ApiParam(value = "Domain GUIDs", required = false)List<String> domainGuids, @RequestParam(required = false) @ApiParam(value = "hosts", required = false)List<String> hosts, @RequestParam(required = false) @ApiParam(value = "Org GUIDs", required = false)List<String> orgGuids, @RequestParam(required = false) @ApiParam(value = "Space GUIDs", required = false)List<String> spaceGuids) throws Exception {
        return appServiceV3.listRoutes(appGuid, domainGuids, hosts, orgGuids, spaceGuids);
    }

    /*
     * 앱 Task 리스트 조회
     * @param String appGuid
     */
    @ApiOperation(value = "Application Task 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/listTasks"})
    public ListApplicationTasksResponse listTasks(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid, @RequestParam(required = false) @ApiParam(value = "Application 이름들", required = false)List<String> names, @RequestParam(required = false) @ApiParam(value = "sequenceGuids GUIDs", required = false)List<String> sequenceGuids, @RequestParam(required = false) @ApiParam(value = "task GUIDs", required = false)List<String> taskGuids) throws Exception {
        return appServiceV3.listTasks(appGuid, names, sequenceGuids, taskGuids);
    }

    /*
     * 앱 재시작
     * @param String appGuid
     */
    @ApiOperation(value = "Application 재시작")
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/restart"})
    public RestartApplicationResponse restart(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.restart(appGuid);
    }

    /*
     * 앱 확장
     * @param Application app(instance, disk, memory), String appGuid
     */
    @ApiOperation(value = "Application 확장/축소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instances", value = "Application Instances", required = false, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "diskInMb", value = "Application Disk (MB) ", required = false, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "memoryInMb", value = "Application Memory (MB)", required = false, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/scale"})
    public ScaleApplicationResponse scale(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData, @PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        // Integer 시 value 없을 시 exception 처리
        Integer instances = Integer.valueOf(stringNullCheck(requestData.get("instances")));
        Integer diskInMb = Integer.valueOf(stringNullCheck(requestData.get("diskInMb")));
        Integer memoryInMb = Integer.valueOf(stringNullCheck(requestData.get("memoryInMb")));

        Application app = new Application();

        app.setInstances(instances);
        app.setDiskQuota(diskInMb);
        app.setMemoryInMb(memoryInMb);

        return appServiceV3.scale(app, appGuid);
    }

    @ApiOperation(value = "Application Droplet 세팅")
    @PatchMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/setCurrentDroplet/{dropletGuid}"})
    public SetApplicationCurrentDropletResponse setCurrentDroplet(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid, @PathVariable @ApiParam(value = "Droplet GUID", required = true)String dropletGuid) throws Exception {
        return appServiceV3.setCurrentDroplet(appGuid, dropletGuid);
    }

    @ApiOperation(value = "Application 시작")
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/start"})
    public StartApplicationResponse start(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.start(appGuid);
    }

    @ApiOperation(value = "Application 정지")
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/stop"})
    public StopApplicationResponse stop(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.stop(appGuid);
    }

    /*
     * 앱 이름 변경
     * @param Application app(name), String appGuid
     */
    @ApiOperation(value = "Application 앱 이름 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Application 이름", required = true, paramType = "body", dataType = "string")
    })
    @PatchMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}"})
    public UpdateApplicationResponse update(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData, @PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        if ( name.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }
        Application app = new Application();
        app.setName(name);
        return appServiceV3.update(app, appGuid);
    }

    /*
     * 환경변수 변경
     * @param Application app(name), String appGuid
     */
    @ApiOperation(value = "Application 환경변수 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "environmentVariables", value = "Application 환경변수 (key, value)", required = true, paramType = "body", dataType = "string")
    })
    @PatchMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/updateEnvironmentVariables"})
    public UpdateApplicationEnvironmentVariablesResponse updateEnvironmentVariables(@RequestBody @ApiParam(hidden = true)Map<String, Map.Entry<String, ? extends String>> requestData, @PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        Map.Entry<String, ? extends String> environmentVariables = requestData.get("environmentVariables");
        // null 체크 필요
        //배열 변수 넘어오는거 체크 필요
        Application app = new Application();
        app.setEnvironmentVariables(environmentVariables);
        return appServiceV3.updateEnvironmentVariables(app, appGuid);
    }

    @ApiOperation(value = "Application 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/get"})
    public GetApplicationResponse get(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return appServiceV3.get(appGuid);
    }

    @ApiOperation(value = "Application 패키지 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/apps/{appGuid}/listPackages"})
    public ListApplicationPackagesResponse listPackages(@PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid, @RequestParam(required = false) @ApiParam(value = "Package GUIDs", required = false)List<String> packageGuids) throws Exception {
        return appServiceV3.listPackages(appGuid, packageGuids);
    }

}
