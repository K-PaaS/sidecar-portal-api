package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.routes.Process;
import org.cloudfoundry.client.v3.routes.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Route;
import org.springframework.stereotype.Service;

@Service
public class RoutesServiceV3 extends Common {
    public CreateRouteResponse create(Route route, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .create(CreateRouteRequest
                        .builder()
                        .relationships(route.getRelationships())
                        .host(route.getHost())
                        .build())
                .block();

        /*return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .create(CreateRouteRequest
                        .builder()
                        .relationships(RouteRelationships
                                .builder()
                                .domain(ToOneRelationship
                                        .builder()
                                        .data(Relationship
                                                .builder()
                                                .id("default-domain")
                                                .build())
                                        .build())
                                .space(ToOneRelationship
                                        .builder()
                                        .data(Relationship
                                                .builder()
                                                .id("cf-space-36ab0f6a-7cb4-4f4d-aa88-382a9dc466cb")
                                                .build())
                                        .build())
                                .build())
                        .host("host")
                        .build())
                .block();*/
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .delete(DeleteRouteRequest
                        .builder()
                        .routeId(guid)
                        .build())
                .block();
    }

    public GetRouteResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .get(GetRouteRequest
                        .builder()
                        .routeId(guid)
                        .build())
                .block();
    }

    public InsertRouteDestinationsResponse insertDestinations(String routeGuid, String appGuid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .insertDestinations(InsertRouteDestinationsRequest
                        .builder()
                        .routeId(routeGuid)
                        .destination(Destination
                                .builder()
                                .application(Application.builder().applicationId(appGuid).process(Process.builder().type("web").build()).build())
                                .build())
                        .build())
                .block();
    }

    public ListRoutesResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .list(ListRoutesRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public ListRouteDestinationsResponse listDestinations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .listDestinations(ListRouteDestinationsRequest
                        .builder()
                        .routeId(guid)
                        .build())
                .block();
    }

    public Void removeDestinations(String routeGuid, String destinationGuid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .routesV3()
                .removeDestinations(RemoveRouteDestinationsRequest
                        .builder()
                        .routeId(routeGuid)
                        .destinationId(destinationGuid)
                        .build())
                .block();
    }

    public ReplaceRouteDestinationsResponse replaceDestinations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routesV3().replaceDestinations(ReplaceRouteDestinationsRequest.builder().build()).block();
    }

    public UpdateRouteResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).routesV3().update(UpdateRouteRequest.builder().build()).block();
    }
}
