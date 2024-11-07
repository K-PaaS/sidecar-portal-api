package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.GetOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.roles.CreateRoleResponse;
import org.cloudfoundry.client.v3.roles.ListRolesResponse;
import org.cloudfoundry.client.v3.roles.RoleRelationships;
import org.cloudfoundry.client.v3.roles.RoleType;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Organization;
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
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("RolesServiceV3 Test")
public class RolesServiceV3Test {
    @InjectMocks
    RolesServiceV3 rolesServiceV3;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
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

    private static Organization organization = null;
    private static Role role = null;

    private static String orgGuid = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("role-org");

        // Role 객체 생성
        role = new Role();
        role.setType(RoleType.ORGANIZATION_MANAGER);

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        rolesServiceV3.apiHost = apiHost;
        rolesServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Role")
    public void test1_createRole() throws Exception {
        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();

        // Role 생성
        role.setRelationships(RoleRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).user(ToOneRelationship.builder().data(Relationship.builder().id(RoleType.ORGANIZATION_MANAGER.getValue()).build()).build()).build());
        CreateRoleResponse result = rolesServiceV3.create(role);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("List Roles")
    public void test2_listRoles() throws Exception {
        List<String> orgNames = new ArrayList<>();
        orgNames.add(organization.getName());

        ListOrganizationsResponse orgLists = organizationsServiceV3.list(orgNames);

        GetOrganizationResponse orgGet = organizationsServiceV3.get(orgLists.getResources().get(0).getId());
        orgGuid = orgGet.getId();
        Assert.assertNotNull(orgGet.getId());

        List<String> orgGuids = new ArrayList<>();
        orgGuids.add(orgGuid);

        ListRolesResponse result = rolesServiceV3.list(orgGuids, null, null, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Role")
    public void test3_deleteRole() throws Exception {
        // Delete Role
        List<String> orgNames = new ArrayList<>();
        orgNames.add(organization.getName());

        ListOrganizationsResponse orgLists = organizationsServiceV3.list(orgNames);

        GetOrganizationResponse orgGet = organizationsServiceV3.get(orgLists.getResources().get(0).getId());
        orgGuid = orgGet.getId();
        Assert.assertNotNull(orgGet.getId());

        List<String> orgGuids = new ArrayList<>();
        orgGuids.add(orgGuid);

        ListRolesResponse lists = rolesServiceV3.list(orgGuids, null, null, null, null);
        String result = rolesServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result.contains(lists.getResources().get(0).getId()));

        // Organization 삭제
        String orgResult = organizationsServiceV3.delete(orgLists.getResources().get(0).getId());
        Assert.assertTrue(orgResult.contains(orgLists.getResources().get(0).getId()));
    }
}