package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.builds.GetBuildResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

/**
 *  Builds Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BuildsServiceTest {
    @InjectMocks
    BuildsService buildsService;
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
    String packageGuids;
    String buildGuids;

    @Before
    public void setUp() {

        packageGuids = "82dfeb8d-5c94-49b3-b473-b52003094d64";
        buildGuids = "796156ca-70d8-4bd0-ad61-ac2d361271b6";

        params = new Params();
        params.setClusterToken(adminToken);

        buildsService.apiHost = apiHost;
        buildsService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Build")
    public void test1_createBuild() {
        CreateBuildResponse result = buildsService.create(packageGuids);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Build")
    public void test2_getBuild() {
        GetBuildResponse result = buildsService.get(buildGuids);
        Assert.assertNotNull(result.getId());
    }
}