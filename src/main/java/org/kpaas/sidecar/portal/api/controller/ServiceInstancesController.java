package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.serviceinstances.CreateServiceInstanceResponse;
import org.cloudfoundry.client.v3.serviceinstances.ListServiceInstancesResponse;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;
import org.kpaas.sidecar.portal.api.service.ServiceInstancesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ServiceInstancesController {
    @Autowired
    private ServiceInstancesServiceV3 serviceInstancesServiceV3;

    @GetMapping(value = {"/serviceInstances/list"})
    public ListServiceInstancesResponse list(@RequestParam(required = false) List<String> spaceGuids, @RequestParam(required = false) List<String> serviceInstanceNames) throws Exception {
        return serviceInstancesServiceV3.list(spaceGuids, serviceInstanceNames);
    }

    @PostMapping(value = {"/serviceInstances"})
    public CreateServiceInstanceResponse create(@RequestBody ServiceInstance serviceInstance) throws Exception {
        return serviceInstancesServiceV3.create(serviceInstance);
    }

    @DeleteMapping(value = {"/serviceInstances/{serviceInstanceGuid}"})
    public Optional<String> delete(@PathVariable String serviceInstanceGuid) throws Exception {
        return serviceInstancesServiceV3.delete(serviceInstanceGuid);
    }
}
