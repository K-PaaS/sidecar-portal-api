package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.packages.*;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.service.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class PackagesController {
    @Autowired
    private PackagesService packagesService;

    @ApiOperation(value = "Package copy")
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{sourcePackageGuid}/copy/{appGuid}"})
    public CopyPackageResponse copy(@PathVariable @ApiParam(value = "Package Source GUID", required = true)String sourcePackageGuid, @PathVariable @ApiParam(value = "복사할 Application GUID", required = true)String appGuid) throws Exception {
        return packagesService.copy(sourcePackageGuid, appGuid);
    }

    @ApiOperation(value = "Package 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appGuid", value = "Application GUID", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages"})
    public CreatePackageResponse create(@RequestBody @ApiParam(hidden = true) Map<String, String> requestData) throws Exception {
        if (ObjectUtils.isEmpty(requestData.get("appGuid"))) {
            // 추후 exception 처리
            return null;
        }
        return packagesService.create(requestData.get("appGuid"));
    }

    //보류
    /*@GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/download"})
    public Flux<byte[]> download(@PathVariable String packageGuid) throws Exception {
        return packagesService.download(packageGuid);
    }*/

    @ApiOperation(value = "Package 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/get"})
    public GetPackageResponse get(@PathVariable @ApiParam(value = "Package GUID", required = true)String packageGuid) throws Exception {
        return packagesService.get(packageGuid);
    }

    @ApiOperation(value = "Package 목록 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/list"})
    public ListPackagesResponse list(@RequestParam(required = false) @ApiParam(value = "Application GUIDs", required = false)List<String> appGuids, @RequestParam(required = false) @ApiParam(value = "org GUIDs", required = false)List<String> orgGuids, @RequestParam(required = false) @ApiParam(value = "Space GUIDs", required = false)List<String> spaceGuids) throws Exception {
        return packagesService.list(appGuids, orgGuids, spaceGuids);
    }

    @ApiOperation(value = "Package Droplet 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/listDroplets"})
    public ListPackageDropletsResponse listDroplets(@PathVariable @ApiParam(value = "Package GUID", required = true)String packageGuid, @RequestParam(required = false) @ApiParam(value = "Droplet GUIDs", required = false)List<String> dropletGuids) throws Exception {
        return packagesService.listDroplets(packageGuid, dropletGuids);
    }



    @ApiOperation(value = "Package File 업로드")
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/upload"})
    public UploadPackageResponse upload(@PathVariable @ApiParam(value = "Package GUID", required = true)String packageGuid, @RequestParam MultipartFile file) throws Exception {
        return packagesService.upload(packageGuid, file);
    }



}
