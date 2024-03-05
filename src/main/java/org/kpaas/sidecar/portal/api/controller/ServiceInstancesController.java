package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.serviceinstances.CreateServiceInstanceResponse;
import org.cloudfoundry.client.v3.serviceinstances.ListServiceInstancesResponse;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;
import org.kpaas.sidecar.portal.api.service.ServiceInstancesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ServiceInstancesController {
    @Autowired
    private ServiceInstancesServiceV3 serviceInstancesServiceV3;

    @GetMapping(value = {"/serviceInstances/{spaceGuid}/list"})
    public ListServiceInstancesResponse list(@PathVariable String spaceGuid, String token) throws Exception {
        return serviceInstancesServiceV3.list(spaceGuid, token);
    }

    @PostMapping(value = {"/serviceInstances/create"})
    public CreateServiceInstanceResponse create(@RequestBody ServiceInstance serviceInstance, String token) throws Exception {
        return serviceInstancesServiceV3.create(serviceInstance, token);
    }

    @DeleteMapping(value = {"/serviceInstances/{serviceGuid}/delete"})
    public Optional<String> delete(@PathVariable String serviceGuid, String token) throws Exception {
        return serviceInstancesServiceV3.delete(serviceGuid, token);
    }
}
