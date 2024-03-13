package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.spaces.ApplyManifestResponse;
import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.GetSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.kpaas.sidecar.portal.api.model.Space;
import org.kpaas.sidecar.portal.api.service.SpacesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpacesController {
    @Autowired
    private SpacesServiceV3 spacesServiceV3;

    // 미 완성
    @PostMapping(value = {"/spaces/{spaceGuid}/applyManifest"})
    public ApplyManifestResponse applyManifest(@PathVariable String spaceGuid, String token) throws Exception {
        return spacesServiceV3.applyManifest(spaceGuid, token);
    }

    @PostMapping(value = {"/spaces"})
    public CreateSpaceResponse create(@RequestBody Space space, String token) throws Exception {
        return spacesServiceV3.create(space, token);
    }

    @DeleteMapping(value = {"/spaces/{spaceGuid}"})
    public String delete(@PathVariable String spaceGuid, String token) throws Exception {
        return spacesServiceV3.delete(spaceGuid, token);
    }

    @GetMapping(value = {"/spaces/list"})
    public ListSpacesResponse list(@RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> names, String token) throws Exception {
        return spacesServiceV3.list(orgGuids, names, token);
    }

    @GetMapping(value = {"/spaces/{spaceGuid}/get"})
    public GetSpaceResponse get(@PathVariable String spaceGuid, String token) throws Exception {
        return spacesServiceV3.get(spaceGuid, token);
    }

}
