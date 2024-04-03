package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "Route 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "host", value = "host", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "domainGuid", value = "Domain GUID", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "spaceGuid", value = "Space GUID", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes"})
    public CreateRouteResponse create(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
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

    @ApiOperation(value = "Route 삭제")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}"})
    public String delete(@PathVariable @ApiParam(value = "Route GUID", required = true)String routeGuid) throws Exception {
        return routesServiceV3.delete(routeGuid);
    }

    @ApiOperation(value = "Route - Application 연결")
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/insertDestinations/{appGuid}"})
    public InsertRouteDestinationsResponse insertDestinations(@PathVariable @ApiParam(value = "Route GUID", required = true)String routeGuid, @ApiParam(value = "Application GUID", required = true)@PathVariable String appGuid) throws Exception {
        return routesServiceV3.insertDestinations(routeGuid, appGuid);
    }

    @ApiOperation(value = "Route 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/list"})
    public ListRoutesResponse list(@RequestParam(required = false)@ApiParam(value = "Application GUIDs", required = false)List<String> appGuids, @RequestParam(required = false)@ApiParam(value = "Domain GUIDs", required = false)List<String> domainGuids, @RequestParam(required = false)@ApiParam(value = "hosts", required = false)List<String> hosts, @RequestParam(required = false)@ApiParam(value = "Org GUIDs", required = false)List<String> orgGuids, @RequestParam(required = false)@ApiParam(value = "Paths", required = false)List<String> paths, @RequestParam(required = false)@ApiParam(value = "Ports", required = false)List<Integer> ports, @RequestParam(required = false)@ApiParam(value = "Space GUIDs", required = false)List<String> spaceGuids) throws Exception {
        return routesServiceV3.list(appGuids, domainGuids, hosts, orgGuids, paths, ports, spaceGuids);
    }

    @ApiOperation(value = "Route - Application 연결 해제")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/removeDestinations/{appGuid}"})
    public Void removeDestinations(@PathVariable @ApiParam(value = "Route GUID", required = true)String routeGuid, @PathVariable @ApiParam(value = "Application GUID", required = true)String appGuid) throws Exception {
        return routesServiceV3.removeDestinations(routeGuid, appGuid);
    }

    @ApiOperation(value = "Route 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/get"})
    public GetRouteResponse get(@PathVariable @ApiParam(value = "Route GUID", required = true)String routeGuid) throws Exception {
        return routesServiceV3.get(routeGuid);
    }

    @ApiOperation(value = "Route Destination 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/routes/{routeGuid}/listDestinations"})
    public ListRouteDestinationsResponse listDestinations(@PathVariable @ApiParam(value = "Route GUID", required = true)String routeGuid, @RequestParam(required = false) @ApiParam(value = "Application GUIDs", required = false)List<String> appGuids) throws Exception {
        return routesServiceV3.listDestinations(routeGuid, appGuids);
    }
}
