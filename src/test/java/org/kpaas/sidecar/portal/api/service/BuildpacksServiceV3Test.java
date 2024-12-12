package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.buildpacks.ListBuildpacksResponse;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;

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

import static org.mockito.Mockito.when;

/**
 *  Buildpacks Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("BuildpacksServiceV3 Test")
public class BuildpacksServiceV3Test {
    @InjectMocks
    BuildpacksServiceV3 buildpacksServiceV3;
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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        params = new Params();
        params.setClusterToken(adminToken);

        buildpacksServiceV3.apiHost = apiHost;
        buildpacksServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("List Buildpack")
    public void listBuildpack() {
        ListBuildpacksResponse result = buildpacksServiceV3.list();
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }
}