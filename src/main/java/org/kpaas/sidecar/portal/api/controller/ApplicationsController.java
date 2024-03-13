package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.applications.*;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.service.ApplicationsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationsController {

    @Autowired
    private ApplicationsServiceV3 appServiceV3;

    /*
    * 앱 생성
    * @RequestBody Application app(name, relationships(space(data(guid)))
    */
    @PostMapping(value = {"/apps"})
    public CreateApplicationResponse create(@RequestBody Application app, String token) throws Exception {
        return appServiceV3.create(app, token);
    }

    /*
    * 앱 삭제
    */
    @DeleteMapping(value = {"/apps/{appGuid}"})
    public String delete(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.delete(appGuid, token);
    }

    /*
     * 앱 현재 Droplet 조회
     */
    @GetMapping(value = {"/apps/{appGuid}/getCurrentDroplet"})
    public GetApplicationCurrentDropletResponse getCurrentDroplet(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.getCurrentDroplet(appGuid, token);
    }

    /*
     * 앱 환경변수 조회
     */
    @GetMapping(value = {"/apps/{appGuid}/getEnvironment"})
    public GetApplicationEnvironmentResponse getEnvironment(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.getEnvironment(appGuid, token);
    }

    /*
     * 앱 프로세스 조회
     */
    @GetMapping(value = {"/apps/{appGuid}/getProcess"})
    public GetApplicationProcessResponse getProcess(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.getProcess(appGuid, token);
    }

    /*
     * 앱 SSH 가능 조회
     */
    @GetMapping(value = {"/apps/{appGuid}/getSshEnabled"})
    public GetApplicationSshEnabledResponse getSshEnabled(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.getSshEnabled(appGuid, token);
    }

    /*
     * 앱 리스트 조회
     */
    @GetMapping(value = {"/apps/list"})

    public ListApplicationsResponse list(@RequestParam(required = false) List<String> names, @RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids, String token) throws Exception {
        return appServiceV3.list(names, orgGuids, spaceGuids, token);
    }

    /*
     * 앱 프로세스 리스트 조회
     * @param String appGuid
     */
    @GetMapping(value = {"/apps/{appGuid}/listProcesses"})
    public ListApplicationProcessesResponse listProcesses(@PathVariable String appGuid, @RequestParam(required = false)List<String> processGuids, String token) throws Exception {
        return appServiceV3.listProcesses(appGuid, processGuids, token);
    }

    /*
     * 앱 라우트 리스트 조회
     * @param String appGuid
     */
    @GetMapping(value = {"/apps/{appGuid}/listRoutes"})
    public ListApplicationRoutesResponse listRoutes(@PathVariable String appGuid, @RequestParam(required = false) List<String> domainGuids, @RequestParam(required = false) List<String> hosts, @RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids, String token) throws Exception {
        return appServiceV3.listRoutes(appGuid, domainGuids, hosts, orgGuids, spaceGuids, token);
    }

    /*
     * 앱 Task 리스트 조회
     * @param String appGuid
     */
    @GetMapping(value = {"/apps/{appGuid}/listTasks"})
    public ListApplicationTasksResponse listTasks(@PathVariable String appGuid, @RequestParam(required = false) List<String> names, @RequestParam(required = false) List<String> sequenceGuids, @RequestParam(required = false) List<String> taskGuids, String token) throws Exception {
        return appServiceV3.listTasks(appGuid, names, sequenceGuids, taskGuids, token);
    }

    /*
     * 앱 재시작
     * @param String appGuid
     */
    @PostMapping(value = {"/apps/{appGuid}/restart"})
    public RestartApplicationResponse restart(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.restart(appGuid, token);
    }

    /*
     * 앱 확장
     * @param Application app(instance, disk, memory), String appGuid
     */
    @PostMapping(value = {"/apps/{appGuid}/scale"})
    public ScaleApplicationResponse scale(@RequestBody Application app, @PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.scale(app, appGuid, token);
    }

    @PatchMapping(value = {"/apps/{appGuid}/setCurrentDroplet/{dropletGuid}"})
    public SetApplicationCurrentDropletResponse setCurrentDroplet(@PathVariable String appGuid, @PathVariable String dropletGuid,String token) throws Exception {
        return appServiceV3.setCurrentDroplet(appGuid, dropletGuid, token);
    }

    @PostMapping(value = {"/apps/{appGuid}/start"})
    public StartApplicationResponse start(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.start(appGuid, token);
    }

    @PostMapping(value = {"/apps/{appGuid}/stop"})
    public StopApplicationResponse stop(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.stop(appGuid, token);
    }

    /*
     * 앱 이름 변경
     * @param Application app(name), String appGuid
     */
    @PatchMapping(value = {"/apps/{appGuid}"})
    public UpdateApplicationResponse update(@RequestBody Application app, @PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.update(app, appGuid, token);
    }

    /*
     * 환경변수 변경
     * @param Application app(name), String appGuid
     */
    @PatchMapping(value = {"/apps/{appGuid}/updateEnvironmentVariables"})
    public UpdateApplicationEnvironmentVariablesResponse updateEnvironmentVariables(@RequestBody Application app, @PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.updateEnvironmentVariables(app, appGuid, token);
    }

    @GetMapping(value = {"/apps/{appGuid}/get"})
    public GetApplicationResponse get(@PathVariable String appGuid, String token) throws Exception {
        return appServiceV3.get(appGuid, token);
    }

    @GetMapping(value = {"/apps/{appGuid}/listPackages"})
    public ListApplicationPackagesResponse listPackages(@PathVariable String appGuid, @RequestParam(required = false) List<String> packageGuids, String token) throws Exception {
        return appServiceV3.listPackages(appGuid, packageGuids, token);
    }

}
