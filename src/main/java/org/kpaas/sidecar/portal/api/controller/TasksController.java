package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.tasks.GetTaskResponse;
import org.cloudfoundry.client.v3.tasks.ListTasksResponse;
import org.kpaas.sidecar.portal.api.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TasksController {
    @Autowired
    private TasksService tasksService;

    @GetMapping(value = {"/tasks/list"})
    public ListTasksResponse list(String token) throws Exception {
        return tasksService.list(token);
    }

    @GetMapping(value = {"/tasks/{taskGuid}/get"})
    public GetTaskResponse get(@PathVariable String taskGuid, String token) throws Exception {
        return tasksService.get(taskGuid, token);
    }
}
