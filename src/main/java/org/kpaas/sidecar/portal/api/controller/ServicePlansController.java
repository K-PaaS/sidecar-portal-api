package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.serviceplans.ListServicePlansResponse;
import org.kpaas.sidecar.portal.api.service.ServicePlansServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicePlansController {
    @Autowired
    private ServicePlansServiceV3 servicePlansServiceV3;

    @GetMapping(value = {"/servicePlans/list"})
    public ListServicePlansResponse list(String token) throws Exception {
        return servicePlansServiceV3.list(token);
    }
}
