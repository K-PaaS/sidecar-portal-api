package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.roles.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceV3 extends Common {
    public CreateRoleResponse create(Role role, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .rolesV3()
                .create(CreateRoleRequest
                        .builder()
                        .type(role.getType())
                        .relationships(role.getRelationships())
                        .build()
                ).block();
        /*return cloudFoundryClient(tokenProvider(token))
                .rolesV3()
                .create(CreateRoleRequest
                        .builder()
                        .type(RoleType.ORGANIZATION_USER)
                        .relationships(RoleRelationships
                                .builder()
                                .organization(ToOneRelationship
                                        .builder()
                                        .data(Relationship
                                                .builder()
                                                .id("cf-org-664de7b9-6bcb-4bf5-8b19-f8b41b025f01")
                                                .build())
                                        .build())
                                .user(ToOneRelationship
                                        .builder()
                                        .data(Relationship
                                                .builder()
                                                .id("test")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .block();*/
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .rolesV3()
                .delete(DeleteRoleRequest
                        .builder()
                        .roleId(guid)
                        .build())
                .block();
    }

    public GetRoleResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).rolesV3().get(GetRoleRequest.builder().build()).block();
    }

    public ListRolesResponse list(List<String> orgGuids, List<String> spaceGuids, List<String> usernames, List<RoleType> types, String roleGuid, String token) {
        orgGuids = stringListNullCheck(orgGuids);
        spaceGuids = stringListNullCheck(spaceGuids);
        usernames = stringListNullCheck(usernames);

        if ( types == null || types.isEmpty() ){
            types = new ArrayList<>();
        }

        return cloudFoundryClient(tokenProvider(token))
                .rolesV3()
                .list(ListRolesRequest
                        .builder()
                        .organizationIds(orgGuids)
                        .spaceIds(spaceGuids)
                        .userIds(usernames)
                        .types(types)
                        .roleIds(roleGuid)
                        .build())
                .block();
    }
}
