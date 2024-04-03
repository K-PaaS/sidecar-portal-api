package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "Droplet 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/dropletes/{dropletsGuid}/get"})
    public GetDropletResponse get(@PathVariable @ApiParam(value = "Droplet GUID", required = true)String dropletsGuid) throws Exception {
        return dropletsService.get(dropletsGuid);
    }
}
