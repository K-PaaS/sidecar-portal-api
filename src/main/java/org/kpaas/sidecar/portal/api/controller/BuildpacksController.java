package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiOperation;
import org.cloudfoundry.client.v3.buildpacks.ListBuildpacksResponse;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.service.BuildpacksServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildpacksController {
    @Autowired
    private BuildpacksServiceV3 buildpacksServiceV3;

    @ApiOperation(value = "빌드팩 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/buildpacks/list"})
    public ListBuildpacksResponse list() throws Exception {
        return buildpacksServiceV3.list();
    }
}
