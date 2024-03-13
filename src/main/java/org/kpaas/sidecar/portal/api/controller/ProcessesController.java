package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.processes.*;
import org.kpaas.sidecar.portal.api.model.Process;
import org.kpaas.sidecar.portal.api.service.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = {"/processes/list"})
    public ListProcessesResponse list(@RequestParam(required = false) List<String> appGuids, @RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids, String token) throws Exception {
        return processesService.list(appGuids, orgGuids, spaceGuids, token);
    }

    @PatchMapping(value = {"/processes/{processGuid}"})
    public UpdateProcessResponse update(@PathVariable String processGuid, @RequestBody Process process, String token) throws Exception {
        return processesService.update(processGuid, process, token);
    }

    @PostMapping(value = {"/processes/{processGuid}/scale"})
    public ScaleProcessResponse scale(@PathVariable String processGuid, @RequestBody Process process, String token) throws Exception {
        return processesService.scale(processGuid, process, token);
    }
}
