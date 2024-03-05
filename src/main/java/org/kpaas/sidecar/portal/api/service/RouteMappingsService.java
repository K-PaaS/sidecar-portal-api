package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.routemappings.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class RouteMappingsService extends Common {
    public CreateRouteMappingResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routeMappings().create(CreateRouteMappingRequest.builder().build()).block();
    }

    public DeleteRouteMappingResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routeMappings().delete(DeleteRouteMappingRequest.builder().build()).block();
    }

    public GetRouteMappingResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routeMappings().get(GetRouteMappingRequest.builder().build()).block();
    }

    public ListRouteMappingsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routeMappings().list(ListRouteMappingsRequest.builder().build()).block();
    }
}
