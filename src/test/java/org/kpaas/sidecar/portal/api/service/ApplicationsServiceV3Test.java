package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.*;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 *  Application Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationsServiceV3Test {
    @InjectMocks
    ApplicationsServiceV3 appServiceV3;
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
    private static Application app = null;

    String orgGuid;
    String spaceGuid;
    String appGuid;
    String domainGuid;
    String dropletGuid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        orgGuid = "cf-org-33f22255-a4b2-4555-86bf-b0d5f2f9b3b0";
        spaceGuid = "cf-space-e874acd9-272d-4852-9d6b-97555916c4b5";
        appGuid = "33f29649-7bee-40ae-a6c9-d0197334c2c4";
        domainGuid = "733804cc-5f4e-4658-9ac6-d9aeebdded6c";
        dropletGuid = "796156ca-70d8-4bd0-ad61-ac2d361271b6";

        app = new Application();
        app.setName("test-application");
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("TEST_KEY", "Key");
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            app.setEnvironmentVariables(entry);
        }
        params = new Params();
        params.setClusterToken(adminToken);

        appServiceV3.apiHost = apiHost;
        appServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Application")
    public void test01_createApplication() throws Exception {
        CreateApplicationResponse result = appServiceV3.create(app);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Application")
    public void test02_getApplication() throws Exception {
        GetApplicationResponse result = appServiceV3.get(appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("List Applications")
    public void test03_listApplications() throws Exception {
        ListApplicationsResponse result = appServiceV3.list(null, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Get Process")
    public void test04_getProcess() throws Exception {
        GetApplicationProcessResponse result = appServiceV3.getProcess(appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("List Application Processes")
    public void test05_listApplicationProcesses() throws Exception {
        ListApplicationProcessesResponse result = appServiceV3.listProcesses(appGuid, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Get Application Process Statistics")
    public void test06_getProcessStatistics() throws Exception {
        GetApplicationProcessStatisticsResponse result = appServiceV3.getProcessStatistics(appGuid);
        Assert.assertNotNull(result.getResources());
    }

    @Test
    @DisplayName("Get Application Ssh Enabled")
    public void test07_getApplicationSshEnabled() throws Exception {
        GetApplicationSshEnabledResponse result = appServiceV3.getSshEnabled(appGuid);
        Assert.assertNotNull(result.getEnabled());
    }

    @Test
    @DisplayName("List Application Routes")
    public void test08_listApplicationRoutes() throws Exception {
        ListApplicationRoutesResponse result = appServiceV3.listRoutes(appGuid, null, null, null, null);
        Assert.assertNotNull(result.getPagination().getTotalResults());
    }

    @Test
    @DisplayName("List Application Packages")
    public void test09_listApplicationPackages() throws Exception {
        List<String> packageGuids = new ArrayList<>();
        packageGuids.add("${sidecar.test.package-guid}");
        ListApplicationPackagesResponse result = appServiceV3.listPackages(appGuid, packageGuids);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Scale Application")
    public void test10_scaleApplication() throws Exception {
        ScaleApplicationResponse result = appServiceV3.scale(app, appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Start Application")
    public void test11_startApplication() throws Exception {
        StartApplicationResponse result = appServiceV3.start(appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Stop Application")
    public void test12_stopApplication() throws Exception {
        StopApplicationResponse result = appServiceV3.stop(appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Restart Application")
    public void test13_restartApplication() throws Exception {
        RestartApplicationResponse result = appServiceV3.restart(appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Update Application")
    public void test14_updateApplication() throws Exception {
        app.setName("test-app");
        UpdateApplicationResponse result = appServiceV3.update(app, appGuid);
        Assert.assertNotNull(result.getName());
    }

    @Test
    @DisplayName("Update Application Environment Variables")
    public void test15_updateApplicationEnvironmentVariables() throws Exception {
        UpdateApplicationEnvironmentVariablesResponse result = appServiceV3.updateEnvironmentVariables(app, appGuid);
        Assert.assertNotNull(result);
    }

    @Test
    @DisplayName("Set Application Current Droplet")
    public void test16_setCurrentDroplet() throws Exception {
        String appGuid = "33f29649-7bee-40ae-a6c9-d0197334c2c4";
        String dropletGuid = "796156ca-70d8-4bd0-ad61-ac2d361271b6";
        SetApplicationCurrentDropletResponse result = appServiceV3.setCurrentDroplet(appGuid, dropletGuid);
        Assert.assertNotNull(result.getData());
    }

    @Test
    @DisplayName("Get Application Current Droplet")
    public void test17_getCurrentDroplet() throws Exception {
        GetApplicationCurrentDropletResponse result = appServiceV3.getCurrentDroplet(appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Application Environment")
    public void test18_GetApplicationEnvironment() throws Exception {
        GetApplicationEnvironmentResponse result = appServiceV3.getEnvironment(appGuid);
        Assert.assertNotNull(result.getApplicationEnvironmentVariables());
    }

    @Test
    @DisplayName("Delete Application")
    public void test19_deleteApplication() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(app.getName());
        ListApplicationsResponse lists = appServiceV3.list(names, null, null);

        String result = appServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result.contains(lists.getResources().get(0).getId()));
    }
}