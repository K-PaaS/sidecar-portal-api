package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.builds.GetBuildResponse;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.service.BuildsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BuildsController {
    @Autowired
    private BuildsService buildsService;

    @ApiOperation(value = "Build 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "packageGuid", value = "Package GUID", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/builds"})
    public CreateBuildResponse create(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
        if (ObjectUtils.isEmpty(requestData.get("packageGuid"))) {
            // 추후 exception 처리
            return null;
        }
        return buildsService.create(requestData.get("packageGuid"));
    }

    @ApiOperation(value = "Build 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/builds/{buildGuid}/get"})
    public GetBuildResponse get(@PathVariable @ApiParam(value = "Build GUID", required = true)String buildGuid) throws Exception {
        return buildsService.get(buildGuid);
    }
}
