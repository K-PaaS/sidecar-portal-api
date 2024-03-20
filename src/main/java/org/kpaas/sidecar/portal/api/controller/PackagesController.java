package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.packages.*;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.service.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
public class PackagesController {
    @Autowired
    private PackagesService packagesService;

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{sourcePackageGuid}/copy/{appGuid}"})
    public CopyPackageResponse copy(@PathVariable String sourcePackageGuid, @PathVariable String appGuid) throws Exception {
        return packagesService.copy(sourcePackageGuid, appGuid);
    }

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages"})
    public CreatePackageResponse create(@RequestBody Map<String, String> requestData) throws Exception {
        if (ObjectUtils.isEmpty(requestData.get("appGuid"))) {
            // 추후 exception 처리
            return null;
        }
        return packagesService.create(requestData.get("appGuid"));
    }

    //보류
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/download"})
    public Flux<byte[]> download(@PathVariable String packageGuid) throws Exception {
        return packagesService.download(packageGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/get"})
    public GetPackageResponse get(@PathVariable String packageGuid) throws Exception {
        return packagesService.get(packageGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/list"})
    public ListPackagesResponse list(@RequestParam(required = false) List<String> appGuids, @RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids) throws Exception {
        return packagesService.list(appGuids, orgGuids, spaceGuids);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/listDroplets"})
    public ListPackageDropletsResponse listDroplets(@PathVariable String packageGuid, @RequestParam(required = false) List<String> dropletGuids) throws Exception {
        return packagesService.listDroplets(packageGuid, dropletGuids);
    }

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/packages/{packageGuid}/upload"})
    public UploadPackageResponse upload(@PathVariable String packageGuid, File file) throws Exception {
        return packagesService.upload(packageGuid, file);
    }



}
