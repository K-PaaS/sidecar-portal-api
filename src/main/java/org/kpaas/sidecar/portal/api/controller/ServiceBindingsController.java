package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.servicebindings.CreateServiceBindingResponse;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsResponse;
import org.kpaas.sidecar.portal.api.model.ServiceBinding;
import org.kpaas.sidecar.portal.api.service.ServiceBindingsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceBindingsController {
    @Autowired
    private ServiceBindingsServiceV3 serviceBindingsServiceV3;

    @GetMapping(value = {"/serviceBindings/list"})
    public ListServiceBindingsResponse list(@RequestParam(required = false) List<String> appGuids, @RequestParam(required = false) List<String> appNames, @RequestParam(required = false) List<String> serviceInstanceGuids, @RequestParam(required = false) List<String> serviceInstanceNames, @RequestParam(required = false) List<String> servicePlanGuids, @RequestParam(required = false) List<String> servicePlanNames) throws Exception {
        return serviceBindingsServiceV3.list(appGuids, appNames, serviceInstanceGuids, serviceInstanceNames, servicePlanGuids, servicePlanNames);
    }

    @PostMapping(value = {"/serviceBindings"})
    public CreateServiceBindingResponse create(@RequestBody ServiceBinding serviceBinding) throws Exception {
        return serviceBindingsServiceV3.create(serviceBinding);
    }

    @DeleteMapping(value = {"/serviceBindings/{serviceBindingGuid}"})
    public String delete(@PathVariable String serviceBindingGuid) throws Exception {
        return serviceBindingsServiceV3.delete(serviceBindingGuid);
    }
}
