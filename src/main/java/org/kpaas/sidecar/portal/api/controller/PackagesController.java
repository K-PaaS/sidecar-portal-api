package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.packages.*;
import org.kpaas.sidecar.portal.api.service.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.File;

@RestController
public class PackagesController {
    @Autowired
    private PackagesService packagesService;

    @GetMapping(value = {"/packages/{sourcePackageGuid}/copy/{appGuid}"})
    public CopyPackageResponse copy(@PathVariable String sourcePackageGuid, @PathVariable String appGuid, String token) throws Exception {
        return packagesService.copy(sourcePackageGuid, appGuid, token);
    }

    @PostMapping(value = {"/packages/{appGuid}/create"})
    public CreatePackageResponse create(@PathVariable String appGuid, String token) throws Exception {
        return packagesService.create(appGuid, token);
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

    @GetMapping(value = {"/packages/{appGuid}/list"})
    public ListPackagesResponse list(@PathVariable String appGuid, String token) throws Exception {
        return packagesService.list(appGuid, token);
    }

    @GetMapping(value = {"/packages/{packageGuid}/listDroplets"})
    public ListPackageDropletsResponse listDroplets(@PathVariable String packageGuid, String token) throws Exception {
        return packagesService.listDroplets(packageGuid, token);
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
