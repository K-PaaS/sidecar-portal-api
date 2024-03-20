package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.serviceinstances.CreateServiceInstanceResponse;
import org.cloudfoundry.client.v3.serviceinstances.ListServiceInstancesResponse;
import org.cloudfoundry.client.v3.serviceinstances.ServiceInstanceRelationships;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;
import org.kpaas.sidecar.portal.api.service.ServiceInstancesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ServiceInstancesController extends Common {
    @Autowired
    private ServiceInstancesServiceV3 serviceInstancesServiceV3;

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances/list"})
    public ListServiceInstancesResponse list(@RequestParam(required = false) List<String> spaceGuids, @RequestParam(required = false) List<String> serviceInstanceNames) throws Exception {
        return serviceInstancesServiceV3.list(spaceGuids, serviceInstanceNames);
    }

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances"})
    public CreateServiceInstanceResponse create(@RequestBody Map<String, String> requestData) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));

        if ( name.isEmpty() || spaceGuid.isEmpty() ){ // 차후 수정

            throw new NullPointerException("NULL 발생");
        }
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setName(name);
        serviceInstance.setRelationships(ServiceInstanceRelationships.builder()
                        .space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build())
                .build());

        return serviceInstancesServiceV3.create(serviceInstance);
    }

    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceInstances/{serviceInstanceGuid}"})
    public Optional<String> delete(@PathVariable String serviceInstanceGuid) throws Exception {
        return serviceInstancesServiceV3.delete(serviceInstanceGuid);
    }
}
