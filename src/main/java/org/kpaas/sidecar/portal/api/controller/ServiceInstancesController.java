package org.kpaas.sidecar.portal.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.serviceinstances.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;
import org.kpaas.sidecar.portal.api.service.ServiceInstancesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ServiceInstancesController extends Common {
    @Autowired
    private ServiceInstancesServiceV3 serviceInstancesServiceV3;

    @ApiOperation(value = "ServiceInstance 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances/list"})
    public ListServiceInstancesResponse list(@RequestParam(required = false) @ApiParam(value = "Space GUIDs", required = false)List<String> spaceGuids, @RequestParam(required = false) @ApiParam(value = "ServiceInstance 이름들", required = false)List<String> serviceInstanceNames) throws Exception {
        return serviceInstancesServiceV3.list(spaceGuids, serviceInstanceNames);
    }

    @ApiOperation(value = "ServiceInstance 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "ServiceInstance 이름", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "spaceGuid", value = "Space GUID", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "credentials", value = "credentials", required = false, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances"})
    public CreateServiceInstanceResponse create(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));
        String credentials = stringNullCheck(requestData.get("credentials"));

        if ( name.isEmpty() || spaceGuid.isEmpty()){ // 차후 수정

            throw new NullPointerException("NULL 발생");
        }
        Map<String, ? extends Object> entries = null;

        if (!credentials.isEmpty()) {
            entries = new ObjectMapper().readValue(credentials, Map.class);
        }
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setName(name);
        serviceInstance.setCredentials(entries);
        serviceInstance.setRelationships(ServiceInstanceRelationships.builder()
                        .space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build())
                .build());
        serviceInstance.setType(ServiceInstanceType.USER_PROVIDED);

        return serviceInstancesServiceV3.create(serviceInstance);
    }

    @ApiOperation(value = "ServiceInstance 삭제")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances/{serviceInstanceGuid}"})
    public Map delete(@PathVariable @ApiParam(value = "ServiceInstance GUID", required = true)String serviceInstanceGuid) throws Exception {
        Map map = new HashMap();
        Optional<String> result = serviceInstancesServiceV3.delete(serviceInstanceGuid);
        map.put("resultMessage", result);
        return map;
    }

    @ApiOperation(value = "ServiceInstance update")
    @PatchMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances/{serviceInstanceGuid}"})
    public UpdateServiceInstanceResponse update(@PathVariable @ApiParam(value = "ServiceInstance GUID", required = true)String serviceInstanceGuid, @RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
        String credentials = stringNullCheck(requestData.get("credentials"));

        Map<String, ? extends Object> entries = null;
        if (!credentials.isEmpty()) {
            entries = new ObjectMapper().readValue(credentials, Map.class);
        }else {
            entries = new ObjectMapper().readValue("{\"\": \"\"}", Map.class);
        }

        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setId(serviceInstanceGuid);
        serviceInstance.setCredentials(entries);

        UpdateServiceInstanceResponse response = serviceInstancesServiceV3.update(serviceInstance);
        return response;
    }
}
