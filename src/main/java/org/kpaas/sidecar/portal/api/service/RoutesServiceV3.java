package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.routes.Process;
import org.cloudfoundry.client.v3.routes.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoutesServiceV3 extends Common {
    public CreateRouteResponse create(Route route) {
        return cloudFoundryClient(tokenProvider())
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

    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider())
                .routesV3()
                .delete(DeleteRouteRequest
                        .builder()
                        .routeId(guid)
                        .build())
                .block();
    }

    public GetRouteResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .routesV3()
                .get(GetRouteRequest
                        .builder()
                        .routeId(guid)
                        .build())
                .block();
    }

    public InsertRouteDestinationsResponse insertDestinations(String routeGuid, String appGuid) {
        return cloudFoundryClient(tokenProvider())
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

    public ListRoutesResponse list(List<String> appGuids, List<String> domainGuids, List<String> hosts, List<String> orgGuids, List<String> paths, List<Integer> ports, List<String> spaceGuids) {
        appGuids = stringListNullCheck(appGuids);
        domainGuids = stringListNullCheck(domainGuids);
        hosts = stringListNullCheck(hosts);
        orgGuids = stringListNullCheck(orgGuids);
        paths = stringListNullCheck(paths);
        spaceGuids = stringListNullCheck(spaceGuids);
        ports = integerListNullCheck(ports);

        return cloudFoundryClient(tokenProvider())
                .routesV3()
                .list(ListRoutesRequest
                        .builder()
                        .applicationIds(appGuids)
                        .domainIds(domainGuids)
                        .hosts(hosts)
                        .organizationIds(orgGuids)
                        .paths(paths)
                        .ports(ports)
                        .spaceIds(spaceGuids)
                        .build())
                .block();
    }

    public ListRouteDestinationsResponse listDestinations(String routeGuid, List<String> appGuids) {
        routeGuid = stringNullCheck(routeGuid);
        appGuids = stringListNullCheck(appGuids);

        return cloudFoundryClient(tokenProvider())
                .routesV3()
                .listDestinations(ListRouteDestinationsRequest
                        .builder()
                        .routeId(routeGuid)
                        .applicationIds(appGuids)
                        .build())
                .block();
    }

    public Void removeDestinations(String routeGuid, String appGuid) {
        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);

        return cloudFoundryClient(tokenProvider())
                .routesV3()
                .removeDestinations(RemoveRouteDestinationsRequest
                        .builder()
                        .routeId(routeGuid)
                        .destinationId(listDestinations(routeGuid, appGuids).getDestinations().get(0).getDestinationId())
                        .build())
                .block();
    }

    public ReplaceRouteDestinationsResponse replaceDestinations(String guid) {
        return cloudFoundryClient(tokenProvider()).routesV3().replaceDestinations(ReplaceRouteDestinationsRequest.builder().build()).block();
    }

    public UpdateRouteResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).routesV3().update(UpdateRouteRequest.builder().build()).block();
    }
}
