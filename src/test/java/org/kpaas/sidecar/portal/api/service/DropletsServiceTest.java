package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.droplets.GetDropletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DropletsServiceTest {
    @InjectMocks
    DropletsService dropletsService;
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

    @Value("${sidecar.test.droplet-guid}")
    private String dropletGuid;

    Params params;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        params = new Params();
        params.setClusterToken(adminToken);

        dropletsService.apiHost = apiHost;
        dropletsService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Get Droplets")
    public void test1_getDroplets() throws Exception {
        GetDropletResponse result = dropletsService.get(dropletGuid);
        Assert.assertNotNull(result);
    }
}