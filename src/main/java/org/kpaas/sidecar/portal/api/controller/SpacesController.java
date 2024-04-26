package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.spaces.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Space;
import org.kpaas.sidecar.portal.api.service.SpacesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SpacesController extends Common {
    @Autowired
    private SpacesServiceV3 spacesServiceV3;

    // 미 완성
    @ApiOperation(value = "Space Manifest 적용")
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/{spaceGuid}/applyManifest"})
    public ApplyManifestResponse applyManifest(@PathVariable @ApiParam(value = "Space GUID", required = true)String spaceGuid) throws Exception {
        return spacesServiceV3.applyManifest(spaceGuid);
    }

    @ApiOperation(value = "Space 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Space 이름", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "orgGuid", value = "Org GUID", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces"})
    public CreateSpaceResponse create(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
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

    @ApiOperation(value = "Space 삭제")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/{spaceGuid}"})
    public Map delete(@PathVariable @ApiParam(value = "Space GUID", required = true)String spaceGuid) throws Exception {
        Map map = new HashMap();
        String result = spacesServiceV3.delete(spaceGuid);
        map.put("resultMessage", result);
        return map;
    }

    @ApiOperation(value = "Space 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/list"})
    public ListSpacesResponse list(@RequestParam(required = false) @ApiParam(value = "Org GUIDs", required = false)List<String> orgGuids, @RequestParam(required = false) @ApiParam(value = "Space 이름들", required = false)List<String> names) throws Exception {
        return spacesServiceV3.list(orgGuids, names);
    }

    @ApiOperation(value = "Space 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/spaces/{spaceGuid}/get"})
    public GetSpaceResponse get(@PathVariable @ApiParam(value = "Space GUID", required = true)String spaceGuid) throws Exception {
        return spacesServiceV3.get(spaceGuid);
    }

}
