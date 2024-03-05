package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.routes.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class RoutesService extends Common {
    public AssociateRouteApplicationResponse associateApplication(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().associateApplication(AssociateRouteApplicationRequest.builder().build()).block();
    }

    public CreateRouteResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().create(CreateRouteRequest.builder().build()).block();
    }

    public DeleteRouteResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().delete(DeleteRouteRequest.builder().build()).block();
    }

    public Boolean exists(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().exists(RouteExistsRequest.builder().build()).block();
    }

    public GetRouteResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().get(GetRouteRequest.builder().build()).block();
    }

    public ListRoutesResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().list(ListRoutesRequest.builder().build()).block();
    }

    public ListRouteApplicationsResponse listApplications(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().listApplications(ListRouteApplicationsRequest.builder().build()).block();
    }

    public ListRouteMappingsResponse listMappings(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().listMappings(ListRouteMappingsRequest.builder().build()).block();
    }

    public Void removeApplication(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().removeApplication(RemoveRouteApplicationRequest.builder().build()).block();
    }

    public UpdateRouteResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routes().update(UpdateRouteRequest.builder().build()).block();
    }
}
