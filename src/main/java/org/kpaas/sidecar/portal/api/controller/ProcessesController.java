package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "Process 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}/get"})
    public GetProcessResponse get(@PathVariable @ApiParam(value = "Process GUID", required = true)String processGuid) throws Exception {
        return processesService.get(processGuid);
    }

    @ApiOperation(value = "Process Stats 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}/getStatistics"})
    public GetProcessStatisticsResponse getStatistics(@PathVariable @ApiParam(value = "Process GUID", required = true)String processGuid) throws Exception {
        return processesService.getStatistics(processGuid);
    }

    @ApiOperation(value = "Process 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/list"})
    public ListProcessesResponse list(@RequestParam(required = false) @ApiParam(value = "Application GUIDs", required = false)List<String> appGuids, @RequestParam(required = false) @ApiParam(value = "Org GUIDs", required = false)List<String> orgGuids, @RequestParam(required = false) @ApiParam(value = "Space GUIDs", required = false)List<String> spaceGuids) throws Exception {
        return processesService.list(appGuids, orgGuids, spaceGuids);
    }
    @ApiOperation(value = "Process Command 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "command", value = "Command", required = true, paramType = "body", dataType = "string")
    })
    @PatchMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}"})
    public UpdateProcessResponse update(@PathVariable @ApiParam(value = "Process GUID", required = true) String processGuid, @RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
        String command = stringNullCheck(requestData.get("command"));
        Process process = new Process();
        process.setCommand(command);
        return processesService.update(processGuid, process);
    }

    @ApiOperation(value = "Process 확장/축소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instances", value = "Application Instances", required = false, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "diskInMb", value = "Application Disk (MB) ", required = false, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "memoryInMb", value = "Application Memory (MB)", required = false, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/processes/{processGuid}/scale"})
    public ScaleProcessResponse scale(@PathVariable @ApiParam(value = "Process GUID", required = true)String processGuid, @ApiParam(hidden = true)@RequestBody Map<String, String> requestData) throws Exception {
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
