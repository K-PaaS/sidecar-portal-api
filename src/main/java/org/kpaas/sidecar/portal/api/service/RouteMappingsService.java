package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.routemappings.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class RouteMappingsService extends Common {
    public CreateRouteMappingResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).routeMappings().create(CreateRouteMappingRequest.builder().build()).block();
    }

    public DeleteRouteMappingResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).routeMappings().delete(DeleteRouteMappingRequest.builder().build()).block();
    }

    public GetRouteMappingResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).routeMappings().get(GetRouteMappingRequest.builder().build()).block();
    }

    public ListRouteMappingsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).routeMappings().list(ListRouteMappingsRequest.builder().build()).block();
    }
}
