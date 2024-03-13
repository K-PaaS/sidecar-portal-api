package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.roles.CreateRoleResponse;
import org.cloudfoundry.client.v3.roles.ListRolesResponse;
import org.cloudfoundry.client.v3.roles.RoleType;
import org.kpaas.sidecar.portal.api.model.Role;
import org.kpaas.sidecar.portal.api.service.RolesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RolesController {
    @Autowired
    private RolesServiceV3 rolesServiceV3;

    @PostMapping(value = {"/roles"})
    public CreateRoleResponse create(@RequestBody Role role, String token) throws Exception {
        return rolesServiceV3.create(role, token);
    }

    @DeleteMapping(value = {"/roles/{roleGuid}"})
    public String delete(@PathVariable String roleGuid, String token) throws Exception {
        return rolesServiceV3.delete(roleGuid, token);
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
    @GetMapping(value = {"/roles/list"})
    public ListRolesResponse list(@RequestParam(required = false) List<String> orgGuids, @RequestParam(required = false) List<String> spaceGuids, @RequestParam(required = false) List<String> usernames, @RequestParam(required = false) List<RoleType> types, @RequestParam(required = false) String roleGuid,String token) throws Exception {
        return rolesServiceV3.list(orgGuids, spaceGuids, usernames, types, roleGuid, token);
    }
}
