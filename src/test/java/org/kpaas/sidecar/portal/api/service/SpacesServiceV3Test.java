package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.spaces.*;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
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
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("SpacesServiceV3 Test")
public class SpacesServiceV3Test {
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
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
    private static Space space = null;
    String orgGuid;
    String spaceGuid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        orgGuid = "cf-org-81050eb7-2bf7-4a5e-9477-a2b0b5960da5";
        spaceGuid = "cf-space-96a0804a-ecd9-4b19-8912-3fd672f987c1";

        space = new Space();
        space.setName("test-space");
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id("cf-org-81050eb7-2bf7-4a5e-9477-a2b0b5960da5").build()).build()).build());

        params = new Params();
        params.setClusterToken(adminToken);

        spacesServiceV3.apiHost = apiHost;
        spacesServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Space")
    public void test1_createSpace() throws Exception {
        CreateSpaceResponse result = spacesServiceV3.create(space);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Space")
    public void test2_getSpace() throws Exception {
        List<String> orgGuids = new ArrayList<>();
        orgGuids.add(orgGuid);
        ListSpacesResponse lists = spacesServiceV3.list(orgGuids, null);

        GetSpaceResponse result = spacesServiceV3.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getName());
    }

    @Test
    @DisplayName("List Spaces")
    public void test3_listSpaces() throws Exception {
        ListSpacesResponse result = spacesServiceV3.list(null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Space")
    public void test4_deleteSpace() throws Exception {
        List<String> orgGuids = new ArrayList<>();
        orgGuids.add(orgGuid);
        List<String> names = new ArrayList<>();
        names.add(space.getName());

        ListSpacesResponse lists = spacesServiceV3.list(orgGuids, names);

        String result = spacesServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result.contains(lists.getResources().get(0).getId()));
    }
}