package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.routes.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Route;
import org.kpaas.sidecar.portal.api.service.RoutesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoutesController extends Common {
    @Autowired
    private RoutesServiceV3 routesServiceV3;

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes"})
    public CreateRouteResponse create(@RequestBody Map<String, String> requestData) throws Exception {
        //host, domainGuid, spaceGuid
        String host = stringNullCheck(requestData.get("host"));
        String domainGuid = stringNullCheck(requestData.get("domainGuid"));
        String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));

        if ( host.isEmpty() || domainGuid.isEmpty() || spaceGuid.isEmpty()){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }
        Route route = new Route();
        route.setHost(host);
        route.setRelationships(RouteRelationships.builder()
                .domain(ToOneRelationship.builder().data(Relationship.builder().id(domainGuid).build()).build())
                .space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build())
                .build());
        return routesServiceV3.create(route);
    }

    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}"})
    public String delete(@PathVariable String routeGuid) throws Exception {
        return routesServiceV3.delete(routeGuid);
    }

    // 추후 재 수정
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/insertDestinations/{appGuid}"})
    public InsertRouteDestinationsResponse insertDestinations(@PathVariable String routeGuid, @PathVariable String appGuid) throws Exception {
        return routesServiceV3.insertDestinations(routeGuid, appGuid);
    }
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/list"})
    public ListRoutesResponse list(@RequestParam(required = false)List<String> appGuids, @RequestParam(required = false)List<String> domainGuids, @RequestParam(required = false)List<String> hosts, @RequestParam(required = false)List<String> orgGuids, @RequestParam(required = false)List<String> paths, @RequestParam(required = false)List<Integer> ports, @RequestParam(required = false)List<String> spaceGuids) throws Exception {
        return routesServiceV3.list(appGuids, domainGuids, hosts, orgGuids, paths, ports, spaceGuids);
    }

    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/removeDestinations/{appGuid}"})
    public Void removeDestinations(@PathVariable String routeGuid, @PathVariable String appGuid) throws Exception {
        return routesServiceV3.removeDestinations(routeGuid, appGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/get"})
    public GetRouteResponse get(@PathVariable String routeGuid) throws Exception {
        return routesServiceV3.get(routeGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/listDestinations"})
    public ListRouteDestinationsResponse listDestinations(@PathVariable String routeGuid, @RequestParam(required = false) List<String> appGuids) throws Exception {
        return routesServiceV3.listDestinations(routeGuid, appGuids);
    }
}
