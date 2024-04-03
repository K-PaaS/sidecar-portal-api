package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.tasks.GetTaskResponse;
import org.cloudfoundry.client.v3.tasks.ListTasksResponse;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TasksController {
    @Autowired
    private TasksService tasksService;

    @ApiOperation(value = "Task 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/tasks/list"})
    public ListTasksResponse list(@RequestParam(required = false) @ApiParam(value = "Application GUIDs", required = false)List<String> appGuids, @RequestParam(required = false) @ApiParam(value = "Org GUIDs", required = false)List<String> orgGuids, @RequestParam(required = false) @ApiParam(value = "Space GUIDs", required = false)List<String> spaceGuids) throws Exception {
        return tasksService.list(appGuids, orgGuids, spaceGuids);
    }

    @ApiOperation(value = "Task 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/tasks/{taskGuid}/get"})
    public GetTaskResponse get(@PathVariable @ApiParam(value = "Task GUID", required = true)String taskGuid) throws Exception {
        return tasksService.get(taskGuid);
    }
}
