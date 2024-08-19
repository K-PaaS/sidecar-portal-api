package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.roles.CreateRoleResponse;
import org.cloudfoundry.client.v3.roles.ListRolesResponse;
import org.cloudfoundry.client.v3.roles.RoleRelationships;
import org.cloudfoundry.client.v3.roles.RoleType;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Role;
import org.kpaas.sidecar.portal.api.service.RolesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("sRolesController")
public class RolesController extends Common {
    @Autowired
    private RolesServiceV3 rolesServiceV3;

    @ApiOperation(value = "Role 생성 (User - Org or Space 연결)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "Role Type \n" +
                    "(organization_user, organization_auditor, organization_manager, organization_billing_manager) : 사용 시 orgGuid 필수\n" +
                    "(space_auditor, space_developer, space_manager, space_supporter) : 사용 시 spaceGuid 필수",
                    required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "user", value = "User 이름", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "orgGuid", value = "Org GUID ", required = false, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "spaceGuid", value = "Space GUID", required = false, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/roles"})
    public CreateRoleResponse create(@ApiParam(hidden = true)@RequestBody Map<String, String> requestData) throws Exception {
        //type, orgGuid, user
        String type = stringNullCheck(requestData.get("type"));
        String orgGuid = stringNullCheck(requestData.get("orgGuid"));
        String spaceGuid = stringNullCheck(requestData.get("spaceGuid"));
        String user = stringNullCheck(requestData.get("user"));

        if ( type.isEmpty()  || user.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }
        Role role = new Role();
        role.setType(RoleType.from(type));

        if( (RoleType.ORGANIZATION_ROLE_TYPES.contains(role.getType()) && orgGuid.isEmpty()) || RoleType.SPACE_ROLE_TYPES.contains(role.getType()) && spaceGuid.isEmpty()){
            throw new NullPointerException("Type 확인 필요 발생");
        }
        if (RoleType.ORGANIZATION_ROLE_TYPES.contains(role.getType())) {
            role.setRelationships(RoleRelationships.builder()
                    .organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build())
                    .user(ToOneRelationship.builder().data(Relationship.builder().id(user).build()).build())
                    .build());
        }else if (RoleType.SPACE_ROLE_TYPES.contains(role.getType())){
            role.setRelationships(RoleRelationships.builder()
                    .space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build())
                    .user(ToOneRelationship.builder().data(Relationship.builder().id(user).build()).build())
                    .build());
        }

        return rolesServiceV3.create(role);
    }

    @ApiOperation(value = "Role 삭제 (User - Org or Space 연결 해제)")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/roles/{roleGuid}"})
    public Map delete(@PathVariable @ApiParam(value = "Role GUID", required = true) String roleGuid) throws Exception {
        Map map = new HashMap();
        String result = rolesServiceV3.delete(roleGuid);
        map.put("resultMessage", result);
        return map;
    }

    /*
    * RoleType enum
    * ORGANIZATION_AUDITOR
    * ORGANIZATION_BILLING_MANAGER
    * ORGANIZATION_MANAGER
    * ORGANIZATION_USER
    * SPACE_AUDITOR
    * SPACE_DEVELOPER
    * SPACE_MANAGER
    */
    @ApiOperation(value = "Role 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/roles/list"})
    public ListRolesResponse list(@RequestParam(required = false) @ApiParam(value = "Org GUIDs", required = false)List<String> orgGuids, @RequestParam(required = false) @ApiParam(value = "Space GUIDs", required = false)List<String> spaceGuids, @RequestParam(required = false) List<String> usernames, @RequestParam(required = false) @ApiParam(value = "Role Types", required = false)List<RoleType> types, @RequestParam(required = false) @ApiParam(value = "Role GUIDs", required = false)List<String> roleGuids) throws Exception {
        return rolesServiceV3.list(orgGuids, spaceGuids, usernames, types, roleGuids);
    }
}

