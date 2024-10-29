package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.roles.CreateRoleResponse;
import org.cloudfoundry.client.v3.roles.ListRolesResponse;
import org.cloudfoundry.client.v3.roles.RoleRelationships;
import org.cloudfoundry.client.v3.roles.RoleType;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 *  Role Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RolesServiceV3Test {
    @InjectMocks
    RolesServiceV3 rolesServiceV3;
    @Mock
    AuthUtil authUtil;

    @Value("${sidecar.apiHost}")
    private String apiHost;

    @Value("${sidecar.tokenKind}")
    private String tokenKind;

    @Value("${sidecar.test.admin-token}")
    private String adminToken;

    @Value("${sidecar.test.org-manager-token}")
    private String orgManagerToken;

    @Value("${sidecar.test.org-user-token}")
    private String orgUserToken;

    @Value("${sidecar.test.space-developer-token}")
    private String spaceDeveloperToken;

    @Value("${sidecar.test.space-user-token}")
    private String spaceUserToken;

    Params params;
    private static Role role = null;
    String orgGuid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        orgGuid = "cf-org-81050eb7-2bf7-4a5e-9477-a2b0b5960da5";

        role = new Role();
        role.setType(RoleType.ORGANIZATION_USER);
        role.setRelationships(RoleRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id("cf-org-81050eb7-2bf7-4a5e-9477-a2b0b5960da5").build()).build()).user(ToOneRelationship.builder().data(Relationship.builder().id("organization_user").build()).build()).build());

        params = new Params();
        params.setClusterToken(adminToken);

        rolesServiceV3.apiHost = apiHost;
        rolesServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Role")
    public void test1_createRole() throws Exception {
        CreateRoleResponse result = rolesServiceV3.create(role);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("List Roles")
    public void test2_listRoles() throws Exception {
        List<String> orgGuids = new ArrayList<>();
        orgGuids.add(orgGuid);
        ListRolesResponse result = rolesServiceV3.list(orgGuids, null, null, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Role")
    public void test3_deleteRole() throws Exception {
        List<String> orgGuids = new ArrayList<>();
        orgGuids.add(orgGuid);
        ListRolesResponse lists = rolesServiceV3.list(orgGuids, null, null, null, null);

        String result = rolesServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result.contains(lists.getResources().get(0).getId()));
    }
}