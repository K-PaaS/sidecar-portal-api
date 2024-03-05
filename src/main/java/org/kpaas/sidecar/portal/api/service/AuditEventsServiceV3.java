package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.auditevents.GetAuditEventRequest;
import org.cloudfoundry.client.v3.auditevents.GetAuditEventResponse;
import org.cloudfoundry.client.v3.auditevents.ListAuditEventsRequest;
import org.cloudfoundry.client.v3.auditevents.ListAuditEventsResponse;
import org.kpaas.sidecar.portal.api.common.Common;

public class AuditEventsServiceV3 extends Common {
    public GetAuditEventResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).auditEventsV3().get(GetAuditEventRequest.builder().build()).block();
    }
    public ListAuditEventsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).auditEventsV3().list(ListAuditEventsRequest.builder().build()).block();
    }
}
