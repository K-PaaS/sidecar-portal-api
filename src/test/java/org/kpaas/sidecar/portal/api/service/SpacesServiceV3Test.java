package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.spaces.*;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Organization;
import org.kpaas.sidecar.portal.api.model.Space;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
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
 *  Space Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("SpacesServiceV3 Test")
public class SpacesServiceV3Test {
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
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
    private static Space space = null;

    private static String orgGuid = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("spaces-org");

        // Space 객체 생성
        space = new Space();
        space.setName("spaces-space");

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        spacesServiceV3.apiHost = apiHost;
        spacesServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Space")
    public void test1_createSpace() throws Exception {
        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();
        Assert.assertNotNull(orgResult);

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse result = spacesServiceV3.create(space);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Space")
    public void test2_getSpace() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(space.getName());
        ListSpacesResponse lists = spacesServiceV3.list(null, names);

        GetSpaceResponse result = spacesServiceV3.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getName());
    }

    @Test
    @DisplayName("List Spaces")
    public void test3_listSpaces() throws Exception {
        // 생성한 Space name 추가
        List<String> names = new ArrayList<>();
        names.add(space.getName());

        // 생성한 Space list 결과 가져오기
        ListSpacesResponse spaceLists = spacesServiceV3.list(null, names);

        // 생성한 Space list 갯수 검증
        Assert.assertTrue(spaceLists.getPagination().getTotalResults() > 0);

        // 전체 Space list 결과 가져오기
            // ListSpacesResponse result = spacesServiceV3.list(null, null);

        // 전체 Space list 갯수 검증
            // Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Space")
    public void test4_deleteSpace() throws Exception {
        // Space 삭제
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        String spaceResult = spacesServiceV3.delete(spaceLists.getResources().get(0).getId());
        Assert.assertTrue(spaceResult.contains(spaceLists.getResources().get(0).getId()));

        // Organization 삭제
        List<String> orgNames = new ArrayList<>();
        orgNames.add(organization.getName());

        ListOrganizationsResponse orgLists = organizationsServiceV3.list(orgNames);

        String orgResult = organizationsServiceV3.delete(orgLists.getResources().get(0).getId());
        Assert.assertTrue(orgResult.contains(orgLists.getResources().get(0).getId()));
    }
}