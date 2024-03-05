package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.processes.*;
import org.kpaas.sidecar.portal.api.model.Process;
import org.kpaas.sidecar.portal.api.service.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessesController {
    @Autowired
    private ProcessesService processesService;

    @GetMapping(value = {"/processes/{processGuid}/get"})
    public GetProcessResponse get(@PathVariable String processGuid, String token) throws Exception {
        return processesService.get(processGuid, token);
    }

    @GetMapping(value = {"/processes/{processGuid}/getStatistics"})
    public GetProcessStatisticsResponse getStatistics(@PathVariable String processGuid, String token) throws Exception {
        return processesService.getStatistics(processGuid, token);
    }

    @GetMapping(value = {"/processes/{appGuid}/list"})
    public ListProcessesResponse list(@PathVariable String appGuid, String token) throws Exception {
        return processesService.list(appGuid, token);
    }

    @GetMapping(value = {"/processes/update"})
    public UpdateProcessResponse update(@RequestBody Process process, String token) throws Exception {
        return processesService.update(process, token);
    }

    @GetMapping(value = {"/processes/scale"})
    public ScaleProcessResponse scale(@RequestBody Process process, String token) throws Exception {
        return processesService.scale(process, token);
    }
}
