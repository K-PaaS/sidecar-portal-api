package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.routes.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class RoutesService extends Common {
    public AssociateRouteApplicationResponse associateApplication(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().associateApplication(AssociateRouteApplicationRequest.builder().build()).block();
    }

    public CreateRouteResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().create(CreateRouteRequest.builder().build()).block();
    }

    public DeleteRouteResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().delete(DeleteRouteRequest.builder().build()).block();
    }

    public Boolean exists(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().exists(RouteExistsRequest.builder().build()).block();
    }

    public GetRouteResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().get(GetRouteRequest.builder().build()).block();
    }

    public ListRoutesResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().list(ListRoutesRequest.builder().build()).block();
    }

    public ListRouteApplicationsResponse listApplications(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().listApplications(ListRouteApplicationsRequest.builder().build()).block();
    }

    public ListRouteMappingsResponse listMappings(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().listMappings(ListRouteMappingsRequest.builder().build()).block();
    }

    public Void removeApplication(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().removeApplication(RemoveRouteApplicationRequest.builder().build()).block();
    }

    public UpdateRouteResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).routes().update(UpdateRouteRequest.builder().build()).block();
    }
}
