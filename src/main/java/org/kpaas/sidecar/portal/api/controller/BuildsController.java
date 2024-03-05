package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.builds.GetBuildResponse;
import org.kpaas.sidecar.portal.api.service.BuildsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildsController {
    @Autowired
    private BuildsService buildsService;

    @PostMapping(value = {"/builds/{packageGuid}/create"})
    public CreateBuildResponse create(@PathVariable String packageGuid, String token) throws Exception {
        return buildsService.create(packageGuid, token);
    }

    @GetMapping(value = {"/builds/{buildGuid}/get"})
    public GetBuildResponse get(@PathVariable String buildGuid, String token) throws Exception {
        return buildsService.get(buildGuid, token);
    }
}
