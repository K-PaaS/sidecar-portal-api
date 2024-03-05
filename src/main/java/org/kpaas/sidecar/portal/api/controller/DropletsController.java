package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.droplets.GetDropletResponse;
import org.kpaas.sidecar.portal.api.service.DropletsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DropletsController {
    @Autowired
    private DropletsService dropletsService;

    @GetMapping(value = {"/dropletes/{dropletsGuid}/get"})
    public GetDropletResponse get(@PathVariable String dropletsGuid, String token) throws Exception {
        return dropletsService.get(dropletsGuid, token);
    }
}
