package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.droplets.GetDropletResponse;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.service.DropletsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DropletsController {
    @Autowired
    private DropletsService dropletsService;

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/dropletes/{dropletsGuid}/get"})
    public GetDropletResponse get(@PathVariable String dropletsGuid) throws Exception {
        return dropletsService.get(dropletsGuid);
    }
}
