package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.applicationusageevents.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ApplicationUsageEventsService extends Common {
    public GetApplicationUsageEventResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationUsageEvents().get(GetApplicationUsageEventRequest.builder().build()).block();
    }
    public ListApplicationUsageEventsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationUsageEvents().list(ListApplicationUsageEventsRequest.builder().build()).block();
    }
    public Void purgeAndReseed(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationUsageEvents().purgeAndReseed(PurgeAndReseedApplicationUsageEventsRequest.builder().build()).block();
    }
}
