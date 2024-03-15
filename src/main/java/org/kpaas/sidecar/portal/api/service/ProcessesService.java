package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.processes.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Process;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessesService extends Common {
    public GetProcessResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .processes()
                .get(GetProcessRequest
                        .builder()
                        .processId(guid)
                        .build())
                .block();
    }

    public GetProcessStatisticsResponse getStatistics(String guid) {
        return cloudFoundryClient(tokenProvider())
                .processes()
                .getStatistics(GetProcessStatisticsRequest
                        .builder()
                        .processId(guid)
                        .build())
                .block();
    }

    public ListProcessesResponse list(List<String> appGuids, List<String> orgGuids, List<String> spaceGuids) {
        appGuids = stringListNullCheck(appGuids);
        orgGuids = stringListNullCheck(orgGuids);
        spaceGuids = stringListNullCheck(spaceGuids);

        return cloudFoundryClient(tokenProvider())
                .processes()
                .list(ListProcessesRequest
                        .builder()
                        .applicationIds(appGuids)
                        .organizationIds(orgGuids)
                        .spaceIds(spaceGuids)
                        .build())
                .block();
    }

    public ScaleProcessResponse scale(String processGuid, Process process) {
        return cloudFoundryClient(tokenProvider())
                .processes()
                .scale(ScaleProcessRequest
                        .builder()
                        .processId(processGuid)
                        .instances(process.getInstances())
                        .memoryInMb(process.getMemoryInMb())
                        .diskInMb(process.getDiskInMb())
                        .build())
                .block();
    }

    public Void terminateInstance(String guid) {
        return cloudFoundryClient(tokenProvider()).processes().terminateInstance(TerminateProcessInstanceRequest.builder().build()).block();
    }

    public UpdateProcessResponse update(String processGuid, Process process) {
        return cloudFoundryClient(tokenProvider())
                .processes()
                .update(UpdateProcessRequest
                        .builder()
                        .processId(processGuid)
                        .healthCheck(process.getHealthCheck())
                        .command(process.getCommand())
                        .build())
                .block();
    }
}
