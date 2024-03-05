package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.routes.*;
import org.kpaas.sidecar.portal.api.model.Route;
import org.kpaas.sidecar.portal.api.service.RoutesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoutesController {
    @Autowired
    private RoutesServiceV3 routesServiceV3;

    @PostMapping(value = {"/routes/create"})
    public CreateRouteResponse create(@RequestBody Route route, String token) throws Exception {
        return routesServiceV3.create(route, token);
    }

    @DeleteMapping(value = {"/routes/{routeGuid}/delete"})
    public String delete(@PathVariable String routeGuid, String token) throws Exception {
        return routesServiceV3.delete(routeGuid, token);
    }

    // 추후 재 수정
    @PostMapping(value = {"/routes/{routeGuid}/insertDestinations/{appGuid}"})
    public InsertRouteDestinationsResponse insertDestinations(@PathVariable String routeGuid, @PathVariable String appGuid,String token) throws Exception {
        return routesServiceV3.insertDestinations(routeGuid, appGuid, token);
    }
    @GetMapping(value = {"/routes/{appGuid}/list"})
    public ListRoutesResponse list(@PathVariable String appGuid, String token) throws Exception {
        return routesServiceV3.list(appGuid, token);
    }

    @DeleteMapping(value = {"/routes/{routeGuid}/removeDestinations/{destinationGuid}"})
    public Void removeDestinations(@PathVariable String routeGuid, @PathVariable String destinationGuid, String token) throws Exception {
        return routesServiceV3.removeDestinations(routeGuid, destinationGuid, token);
    }

    @GetMapping(value = {"/routes/{routeGuid}/get"})
    public GetRouteResponse get(@PathVariable String routeGuid, String token) throws Exception {
        return routesServiceV3.get(routeGuid, token);
    }

    @GetMapping(value = {"/routes/{routeGuid}/listDestinations"})
    public ListRouteDestinationsResponse listDestinations(@PathVariable String routeGuid, String token) throws Exception {
        return routesServiceV3.listDestinations(routeGuid, token);
    }
}
