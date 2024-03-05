package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.processes.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Process;
import org.springframework.stereotype.Service;

@Service
public class ProcessesService extends Common {
    public GetProcessResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .processes()
                .get(GetProcessRequest
                        .builder()
                        .processId(guid)
                        .build())
                .block();
    }

    public GetProcessStatisticsResponse getStatistics(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .processes()
                .getStatistics(GetProcessStatisticsRequest
                        .builder()
                        .processId(guid)
                        .build())
                .block();
    }

    public ListProcessesResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .processes()
                .list(ListProcessesRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public ScaleProcessResponse scale(Process process, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .processes()
                .scale(ScaleProcessRequest
                        .builder()
                        .processId(process.getId())
                        .instances(process.getInstances())
                        .memoryInMb(process.getMemoryInMb())
                        .diskInMb(process.getDiskInMb())
                        .build())
                .block();
    }

    public Void terminateInstance(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).processes().terminateInstance(TerminateProcessInstanceRequest.builder().build()).block();
    }

    public UpdateProcessResponse update(Process process, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .processes()
                .update(UpdateProcessRequest
                        .builder()
                        .processId(process.getId())
                        .healthCheck(process.getHealthCheck())
                        .command(process.getCommand())
                        .build())
                .block();
    }
}
