package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.tasks.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService extends Common {
    public CancelTaskResponse cancel(String guid) {
        return cloudFoundryClient(tokenProvider()).tasks().cancel(CancelTaskRequest.builder().build()).block();
    }

    public CreateTaskResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).tasks().create(CreateTaskRequest.builder().build()).block();
    }

    public GetTaskResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .tasks()
                .get(GetTaskRequest
                        .builder()
                        .taskId(guid)
                        .build())
                .block();
    }

    public ListTasksResponse list(List<String> appGuids, List<String> orgGuids, List<String> spaceGuids) {
        appGuids = stringListNullCheck(appGuids);
        orgGuids = stringListNullCheck(orgGuids);
        spaceGuids = stringListNullCheck(spaceGuids);

        return cloudFoundryClient(tokenProvider())
                .tasks()
                .list(ListTasksRequest
                        .builder()
                        .applicationIds(appGuids)
                        .organizationIds(orgGuids)
                        .spaceIds(spaceGuids)
                        .build())
                .block();
    }
}
