package org.kpaas.sidecar.portal.api.controller;

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

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/tasks/list"})
    public ListTasksResponse list(@RequestParam(required = false)List<String> appGuids, @RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids) throws Exception {
        return tasksService.list(appGuids, orgGuids, spaceGuids);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/tasks/{taskGuid}/get"})
    public GetTaskResponse get(@PathVariable String taskGuid) throws Exception {
        return tasksService.get(taskGuid);
    }
}
