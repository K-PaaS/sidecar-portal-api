package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.serviceusageevents.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceUsageEventsService extends Common {
    public GetServiceUsageEventResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceUsageEvents().get(GetServiceUsageEventRequest.builder().build()).block();
    }

    public ListServiceUsageEventsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceUsageEvents().list(ListServiceUsageEventsRequest.builder().build()).block();
    }

    public Void purgeAndReseed(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceUsageEvents().purgeAndReseed(PurgeAndReseedServiceUsageEventsRequest.builder().build()).block();
    }
}
