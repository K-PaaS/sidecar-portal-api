package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.organizations.*;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Organization;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.Assert;

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
 *  Organization Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("OrganizationsServiceV3 Test")
public class OrganizationsServiceV3Test {
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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        organization = new Organization();
        organization.setName("Organization-org");

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Organization")
    public void test1_createOrganization() throws Exception {
        CreateOrganizationResponse result = organizationsServiceV3.create(organization);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Organization")
    public void test2_getOrganization() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(organization.getName());
        ListOrganizationsResponse lists = organizationsServiceV3.list(names);

        GetOrganizationResponse result = organizationsServiceV3.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getName());
    }

    @Test
    @DisplayName("List Organization")
    public void test3_listOrganizations() throws Exception {
        // 생성한 Organization name 추가
        List<String> names = new ArrayList<>();
        names.add(organization.getName());

        // 생성한 Organization list 결과 가져오기
        ListOrganizationsResponse orgLists = organizationsServiceV3.list(names);

        // 생성한 Organization list 갯수 검증
        Assert.assertTrue(orgLists.getPagination().getTotalResults() > 0);

        // 전체 Organization list 결과 가져오기
            // ListOrganizationsResponse result = organizationsServiceV3.list(null);

        // 전체 Organization list 갯수 검증
            // Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Get Default Domain")
    public void test4_getDefaultDomain() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(organization.getName());
        ListOrganizationsResponse lists = organizationsServiceV3.list(names);

        GetOrganizationResponse orgResult = organizationsServiceV3.get(lists.getResources().get(0).getId());

        GetOrganizationDefaultDomainResponse result = organizationsServiceV3.getDefaultDomain(orgResult.getId());
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("List Domains")
    public void test5_listDomains() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(organization.getName());
        ListOrganizationsResponse lists = organizationsServiceV3.list(names);

        GetOrganizationResponse orgResult = organizationsServiceV3.get(lists.getResources().get(0).getId());

        ListOrganizationDomainsResponse result = organizationsServiceV3.listDomains(orgResult.getId(), null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Organization")
    public void test6_deleteOrganization() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(organization.getName());
        ListOrganizationsResponse lists = organizationsServiceV3.list(names);

        String result = organizationsServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result.contains(lists.getResources().get(0).getId()));
    }
}