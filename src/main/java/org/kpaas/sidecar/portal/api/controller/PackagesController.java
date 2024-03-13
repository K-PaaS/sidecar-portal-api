package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.packages.*;
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

    @PostMapping(value = {"/packages/{sourcePackageGuid}/copy/{appGuid}"})
    public CopyPackageResponse copy(@PathVariable String sourcePackageGuid, @PathVariable String appGuid, String token) throws Exception {
        return packagesService.copy(sourcePackageGuid, appGuid, token);
    }

    @PostMapping(value = {"/packages"})
    public CreatePackageResponse create(@RequestBody Map<String, String> requestData, String token) throws Exception {
        if (ObjectUtils.isEmpty(requestData.get("appGuid"))) {
            // 추후 exception 처리
            return null;
        }
        return packagesService.create(requestData.get("appGuid"), token);
    }

    //보류
    @GetMapping(value = {"/packages/{packageGuid}/download"})
    public Flux<byte[]> download(@PathVariable String packageGuid, String token) throws Exception {
        return packagesService.download(packageGuid, token);
    }

    @GetMapping(value = {"/packages/{packageGuid}/get"})
    public GetPackageResponse get(@PathVariable String packageGuid, String token) throws Exception {
        return packagesService.get(packageGuid, token);
    }

    @GetMapping(value = {"/packages/list"})
    public ListPackagesResponse list(@RequestParam(required = false) List<String> appGuids, @RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids, String token) throws Exception {
        return packagesService.list(appGuids, orgGuids, spaceGuids, token);
    }

    @GetMapping(value = {"/packages/{packageGuid}/listDroplets"})
    public ListPackageDropletsResponse listDroplets(@PathVariable String packageGuid, @RequestParam(required = false) List<String> dropletGuids, String token) throws Exception {
        return packagesService.listDroplets(packageGuid, dropletGuids, token);
    }

    @DeleteMapping(value = {"/packages/{packageGuid}/delete"})
    public String delete(@PathVariable String packageGuid, String token) throws Exception {
        return packagesService.delete(packageGuid, token);
    }

    @PostMapping(value = {"/packages/{packageGuid}/upload"})
    public UploadPackageResponse upload(@PathVariable String packageGuid, File file, String token) throws Exception {
        return packagesService.upload(packageGuid, file, token);
    }



}
