package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.servicebindings.CreateServiceBindingResponse;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsResponse;
import org.kpaas.sidecar.portal.api.model.ServiceBinding;
import org.kpaas.sidecar.portal.api.service.ServiceBindingsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServiceBindingsController {
    @Autowired
    private ServiceBindingsServiceV3 serviceBindingsServiceV3;

    @GetMapping(value = {"/serviceBindings/list"})
    public ListServiceBindingsResponse list(String token) throws Exception {
        return serviceBindingsServiceV3.list(token);
    }

    @PostMapping(value = {"/serviceBindings/create"})
    public CreateServiceBindingResponse create(@RequestBody ServiceBinding serviceBinding, String token) throws Exception {
        return serviceBindingsServiceV3.create(serviceBinding, token);
    }

    @DeleteMapping(value = {"/serviceBindings/{serviceBindingGuid}/delete"})
    public String delete(@PathVariable String serviceBindingGuid, String token) throws Exception {
        return serviceBindingsServiceV3.delete(serviceBindingGuid, token);
    }
}
