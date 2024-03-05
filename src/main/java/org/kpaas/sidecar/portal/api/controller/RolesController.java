package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.roles.CreateRoleResponse;
import org.cloudfoundry.client.v3.roles.ListRolesResponse;
import org.kpaas.sidecar.portal.api.model.Role;
import org.kpaas.sidecar.portal.api.service.RolesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RolesController {
    @Autowired
    private RolesServiceV3 rolesServiceV3;

    @PostMapping(value = {"/roles/create"})
    public CreateRoleResponse create(@RequestBody Role role, String token) throws Exception {
        return rolesServiceV3.create(role, token);
    }

    @DeleteMapping(value = {"/roles/{roleGuid}/delete"})
    public String delete(@PathVariable String roleGuid, String token) throws Exception {
        return rolesServiceV3.delete(roleGuid, token);
    }

    @GetMapping(value = {"/roles/{roleGuid}/list"})
    public ListRolesResponse list(@PathVariable String roleGuid, String token) throws Exception {
        return rolesServiceV3.list(roleGuid, token);
    }
}
