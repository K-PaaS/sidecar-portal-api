package org.kpaas.sidecar.portal.api.controller;

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

import java.util.List;
import java.util.Map;

@RestController("sRolesController")
public class RolesController extends Common {
    @Autowired
    private RolesServiceV3 rolesServiceV3;

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/roles"})
    public CreateRoleResponse create(@RequestBody Map<String, String> requestData) throws Exception {
        //type, orgGuid, user
        String type = stringNullCheck(requestData.get("type"));
        String orgGuid = stringNullCheck(requestData.get("orgGuid"));
        String user = stringNullCheck(requestData.get("user"));

        if ( type.isEmpty() || orgGuid.isEmpty() || user.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }
        Role role = new Role();
        role.setType(RoleType.from(type));
        role.setRelationships(RoleRelationships.builder()
                .organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build())
                .user(ToOneRelationship.builder().data(Relationship.builder().id(user).build()).build())
                .build());
        return rolesServiceV3.create(role);
    }

    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/roles/{roleGuid}"})
    public String delete(@PathVariable String roleGuid) throws Exception {
        return rolesServiceV3.delete(roleGuid);
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
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/roles/list"})
    public ListRolesResponse list(@RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids, @RequestParam(required = false) List<String> usernames, @RequestParam(required = false) List<RoleType> types, @RequestParam(required = false) String roleGuid) throws Exception {
        return rolesServiceV3.list(orgGuids, spaceGuids, usernames, types, roleGuid);
    }
}

