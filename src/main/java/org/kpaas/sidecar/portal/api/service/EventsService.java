package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.events.GetEventRequest;
import org.cloudfoundry.client.v2.events.GetEventResponse;
import org.cloudfoundry.client.v2.events.ListEventsRequest;
import org.cloudfoundry.client.v2.events.ListEventsResponse;
import org.kpaas.sidecar.portal.api.common.Common;

public class EventsService extends Common {
    public GetEventResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).events().get(GetEventRequest.builder().build()).block();
    }

    public ListEventsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).events().list(ListEventsRequest.builder().build()).block();
    }
}
