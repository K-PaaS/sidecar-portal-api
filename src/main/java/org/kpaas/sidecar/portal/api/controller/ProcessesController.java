package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.processes.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Process;
import org.kpaas.sidecar.portal.api.service.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProcessesController extends Common {
    @Autowired
    private ProcessesService processesService;

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}/get"})
    public GetProcessResponse get(@PathVariable String processGuid) throws Exception {
        return processesService.get(processGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}/getStatistics"})
    public GetProcessStatisticsResponse getStatistics(@PathVariable String processGuid) throws Exception {
        return processesService.getStatistics(processGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/list"})
    public ListProcessesResponse list(@RequestParam(required = false) List<String> appGuids, @RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids) throws Exception {
        return processesService.list(appGuids, orgGuids, spaceGuids);
    }

    @PatchMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}"})
    public UpdateProcessResponse update(@PathVariable String processGuid, @RequestBody Map<String, String> requestData) throws Exception {
        String command = stringNullCheck(requestData.get("command"));
        Process process = new Process();
        process.setCommand(command);
        return processesService.update(processGuid, process);
    }

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}/scale"})
    public ScaleProcessResponse scale(@PathVariable String processGuid, @RequestBody Map<String, String> requestData) throws Exception {
        // Integer 시 value 없을 시 exception 처리
        Integer instances = Integer.valueOf(stringNullCheck(requestData.get("instances")));
        Integer diskInMb = Integer.valueOf(stringNullCheck(requestData.get("diskInMb")));
        Integer memoryInMb = Integer.valueOf(stringNullCheck(requestData.get("memoryInMb")));

        Process process = new Process();
        process.setInstances(instances);
        process.setDiskInMb(diskInMb);
        process.setMemoryInMb(memoryInMb);
        return processesService.scale(processGuid, process);
    }
}
