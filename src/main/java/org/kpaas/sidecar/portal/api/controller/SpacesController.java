package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.spaces.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Space;
import org.kpaas.sidecar.portal.api.service.SpacesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SpacesController extends Common {
    @Autowired
    private SpacesServiceV3 spacesServiceV3;

    // 미 완성
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/{spaceGuid}/applyManifest"})
    public ApplyManifestResponse applyManifest(@PathVariable String spaceGuid) throws Exception {
        return spacesServiceV3.applyManifest(spaceGuid);
    }

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces"})
    public CreateSpaceResponse create(@RequestBody Map<String, String> requestData) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        String orgGuid = stringNullCheck(requestData.get("orgGuid"));
        if ( name.isEmpty() || orgGuid.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }

        Space space = new Space();
        space.setName(name);
        space.setRelationships(SpaceRelationships.builder()
                .organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build())
                .build());
        return spacesServiceV3.create(space);
    }

    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/{spaceGuid}"})
    public String delete(@PathVariable String spaceGuid) throws Exception {
        return spacesServiceV3.delete(spaceGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/list"})
    public ListSpacesResponse list(@RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> names) throws Exception {
        return spacesServiceV3.list(orgGuids, names);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/{spaceGuid}/get"})
    public GetSpaceResponse get(@PathVariable String spaceGuid) throws Exception {
        return spacesServiceV3.get(spaceGuid);
    }

}
