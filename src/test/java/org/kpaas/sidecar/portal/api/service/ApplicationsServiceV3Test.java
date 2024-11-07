package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.*;
import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.builds.GetBuildResponse;
import org.cloudfoundry.client.v3.domains.CreateDomainResponse;
import org.cloudfoundry.client.v3.domains.GetDomainResponse;
import org.cloudfoundry.client.v3.domains.ListDomainsResponse;
import org.cloudfoundry.client.v3.droplets.GetDropletResponse;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.packages.*;
import org.cloudfoundry.client.v3.packages.CreatePackageResponse;
import org.cloudfoundry.client.v3.routes.*;
import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.GetSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.cloudfoundry.client.v3.spaces.SpaceRelationships;

import org.kpaas.sidecar.portal.api.model.*;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.model.Route;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.*;
import java.nio.file.Files;
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
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("ApplicationsServiceV3 Test")
public class ApplicationsServiceV3Test {
    @InjectMocks
    ApplicationsServiceV3 appServiceV3;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
    @InjectMocks
    DomainsServiceV3 domainsServiceV3;
    @InjectMocks
    RoutesServiceV3 routesServiceV3;
    @InjectMocks
    PackagesService packagesService;
    @InjectMocks
    BuildsService buildsService;
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

    Params params;

    private static Organization organization = null;
    private static Space space = null;
    private static Domain domain = null;
    private static Application app = null;
    private static Route route = null;

    private static String orgGuid = null;
    private static String spaceGuid = null;
    private static String domainGuid = null;
    private static String routeGuid = null;
    private static String appGuid = null;
    private static String packageGuid = null;
    private static String buildGuid = null;
    private static String dropletGuid = null;

    private static MultipartFile multipartFile = null;

    // build process interval time (sec)
    private final long BUILD_INTERVAL_SECOND = 300;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("app-org");

        // Space 객체 생성
        space = new Space();
        space.setName("app-space");

        // Domain 객체 생성
        domain = new Domain();
        domain.setName("app.com");

        // App 객체 생성
        app = new Application();
        app.setName("application-app");

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("TEST_KEY", "Key");
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            app.setEnvironmentVariables(entry);
        }

        // Route 객체 생성
        route = new Route();
        route.setHost("app-route");
        route.setPath("/v3/domains");

        // File 객체 (프로젝트 내부에 있는 샘플 파일로 설정)
        // File 객체 -> Multipartfile 객체
        File file = new File("src/test/java/org/kpaas/sidecar/portal/api/service/server.zip");
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }

        String mimeType = Files.probeContentType(file.toPath());
        FileItem fileItem = new DiskFileItem("file", mimeType, false, file.getName(), (int) file.length(), file.getParentFile());

        try (InputStream input = new FileInputStream(file);
             OutputStream output = fileItem.getOutputStream()) {
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to create multipart file", ex);
        }

        multipartFile = new CommonsMultipartFile(fileItem);

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        spacesServiceV3.apiHost = apiHost;
        spacesServiceV3.tokenKind = tokenKind;

        domainsServiceV3.apiHost = apiHost;
        domainsServiceV3.tokenKind = tokenKind;

        appServiceV3.apiHost = apiHost;
        appServiceV3.tokenKind = tokenKind;

        routesServiceV3.apiHost = apiHost;
        routesServiceV3.tokenKind = tokenKind;

        packagesService.apiHost = apiHost;
        packagesService.tokenKind = tokenKind;

        buildsService.apiHost = apiHost;
        buildsService.tokenKind = tokenKind;

        dropletsService.apiHost = apiHost;
        dropletsService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Application")
    public void test01_createApplication() throws Exception {
        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceResult = spacesServiceV3.create(space);
        spaceGuid = spaceResult.getId();

        // Domain 생성
        CreateDomainResponse domainResult = domainsServiceV3.create(domain);
        domainGuid = domainResult.getId();
        Assert.assertNotNull(domainResult.getId());

        // Route 생성
        route.setRelationships(RouteRelationships.builder().domain(ToOneRelationship.builder().data(Relationship.builder().id(domainGuid).build()).build()).space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateRouteResponse routeResult = routesServiceV3.create(route);
        Assert.assertNotNull(routeResult.getId());

        // App 생성
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateApplicationResponse appResult = appServiceV3.create(app);
        Assert.assertNotNull(appResult.getId());

        // Package 생성
        CreatePackageResponse packageResult = packagesService.create(appResult.getId());
        Assert.assertNotNull(packageResult.getId());

        // Upload Package
        // App Name 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        // App List 가져오기
        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        // App 정보 가져오기
        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        // Package List 가져오기
        ListPackagesResponse packageLists = packagesService.list(appGuids, null, null);

        // Package 정보 가져오기
        GetPackageResponse packageGet = packagesService.get(packageLists.getResources().get(0).getId());
        packageGuid = packageGet.getId();

        // Upload Package
        UploadPackageResponse packageUpload = packagesService.upload(packageGuid, multipartFile);
        Assert.assertNotNull(packageUpload.getType());
    }

    @Test
    @DisplayName("Get Application")
    public void test02_getApplication() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        Assert.assertNotNull(appResult.getName());
    }

    @Test
    @DisplayName("List Applications")
    public void test03_listApplications() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse result = appServiceV3.list(appNames, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Get Process")
    public void test04_getProcess() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        GetApplicationProcessResponse result = appServiceV3.getProcess(appGuid);
        Assert.assertNotNull(result);
    }

    @Test
    @DisplayName("List Application Processes")
    public void test05_listApplicationProcesses() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        ListApplicationProcessesResponse processLists = appServiceV3.listProcesses(appGuid, null);
        Assert.assertTrue(processLists.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Get Application Process Statistics")
    public void test06_getProcessStatistics() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        GetApplicationProcessStatisticsResponse result = appServiceV3.getProcessStatistics(appGuid);
        Assert.assertNotNull(result.getResources());
    }

    @Test
    @DisplayName("Get Application Ssh Enabled")
    public void test07_getApplicationSshEnabled() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        GetApplicationSshEnabledResponse result = appServiceV3.getSshEnabled(appGuid);
        Assert.assertNotNull(result.getEnabled());
    }

    @Test
    @DisplayName("List Application Routes")
    public void test08_listApplicationRoutes() throws Exception {
        // Insert Destinations
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse spaceGet = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        spaceGuid = spaceGet.getId();

        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);

        ListRoutesResponse routeLists = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);

        GetRouteResponse routeGet = routesServiceV3.get(routeLists.getResources().get(0).getId());

        String routeGuid = routeGet.getId();
        Assert.assertNotNull(routeGet.getId());

        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        InsertRouteDestinationsResponse insertRoute = routesServiceV3.insertDestinations(routeGuid, appGuid);
        Assert.assertNotNull(insertRoute.getDestinations());

        // List Application Routes
        ListApplicationRoutesResponse result = appServiceV3.listRoutes(appGuid, null, null, null, spaceGuids);
        Assert.assertTrue(result.getPagination().getTotalResults() >0 );
    }

    @Test
    @DisplayName("List Application Packages")
    public void test09_listApplicationPackages() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        ListApplicationPackagesResponse result = appServiceV3.listPackages(appGuid, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Scale Application")
    public void test10_scaleApplication() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        ScaleApplicationResponse scaleResult = appServiceV3.scale(app, appGuid);
        Assert.assertNotNull(scaleResult.getId());
    }

    @Test
    @DisplayName("Set Application Current Droplet")
    public void test11_setCurrentDroplet() throws Exception {
        // App name 정보 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        // App List 가져오기
        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        // App 조회정보 가져오기
        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListPackagesResponse lists = packagesService.list(appGuids, null, null);
        GetPackageResponse packageGet = packagesService.get(lists.getResources().get(0).getId());
        String packageGuid = packageGet.getId();

        // Build Create
        CreateBuildResponse buildCreate = buildsService.create(packageGuid);
        String buildGuid = buildCreate.getId();
        Assert.assertNotNull(buildGuid);

        // 현재 시각
        long start = System.currentTimeMillis();

        // 종료 시각
        long end = start + BUILD_INTERVAL_SECOND * 2000;

        // 빌드 확인 중 = STAGED
        GetBuildResponse buildGet = null;
        while (true) {
            buildGet = buildsService.get(buildGuid);
            if (buildGet.getState().getValue().equals("STAGED")) {
                break;
            }
            if ( System.currentTimeMillis() > end) {
                Assert.fail("App Build Time Over");
            }
            Thread.sleep(2000);
        }

        Assert.assertNotNull(buildGet);
        Assert.assertEquals("STAGED" , buildGet.getState().getValue());

        String dropletGuid = buildGet.getDroplet().getId();
        Assert.assertNotNull(dropletGuid);

        GetDropletResponse dropletGet = dropletsService.get(dropletGuid);
        Assert.assertNotNull(dropletGet);
        Assert.assertEquals(dropletGuid, dropletGet.getId());

        // Set Application Current Droplet
        SetApplicationCurrentDropletResponse result = appServiceV3.setCurrentDroplet(appGuid, dropletGuid);
        Assert.assertNotNull(result.getData());
    }

    @Test
    @DisplayName("Get Application Current Droplet")
    public void test12_getCurrentDroplet() throws Exception {
        // App Name 정보 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        // App List 가져오기
        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        // App 조회정보 가져오기
        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appGet.getId();

        // Get Application Current Droplet
        GetApplicationCurrentDropletResponse result = appServiceV3.getCurrentDroplet(appGuid);
        Assert.assertNotNull(result.getStack());
    }

    @Test
    @DisplayName("Start Application")
    public void test13_startApplication() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        StartApplicationResponse result = appServiceV3.start(appGuid);
        Assert.assertNotNull(result.getState());
    }

    @Test
    @DisplayName("Stop Application")
    public void test14_stopApplication() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        StopApplicationResponse result = appServiceV3.stop(appGuid);
        Assert.assertNotNull(result.getState());
    }

    @Test
    @DisplayName("Restart Application")
    public void test15_restartApplication() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        RestartApplicationResponse result = appServiceV3.restart(appGuid);
        Assert.assertNotNull(result.getState());
    }

    @Test
    @DisplayName("Update Application")
    public void test16_updateApplication() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        app.setName("test-app");

        UpdateApplicationResponse result = appServiceV3.update(app, appGuid);
        Assert.assertNotNull(result.getName());
    }

    @Test
    @DisplayName("Update Application Environment Variables")
    public void test17_updateApplicationEnvironmentVariables() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add("test-app");

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        UpdateApplicationEnvironmentVariablesResponse result = appServiceV3.updateEnvironmentVariables(app, appGuid);
        Assert.assertNotNull(result.getVars());
    }

    @Test
    @DisplayName("Get Application Environment")
    public void test18_GetApplicationEnvironment() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add("test-app");

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        GetApplicationEnvironmentResponse result = appServiceV3.getEnvironment(appGuid);
        Assert.assertNotNull(result.getApplicationEnvironmentVariables());
    }

    @Test
    @DisplayName("Delete Application")
    public void test19_deleteApplication() throws Exception {
        // Route 삭제
        List<String> domainNames = new ArrayList<>();
        domainNames.add(domain.getName());

        ListDomainsResponse domainLists = domainsServiceV3.list(domainNames, null);

        GetDomainResponse getDomain = domainsServiceV3.get(domainLists.getResources().get(0).getId());
        domainGuid = getDomain.getId();

        List<String> domainGuids = new ArrayList<>();
        domainGuids.add(domainGuid);

        ListRoutesResponse routeLists = routesServiceV3.list(null, domainGuids, null, null, null, null, null);

        String routeResult = routesServiceV3.delete(routeLists.getResources().get(0).getId());
        Assert.assertTrue(routeResult.contains(routeLists.getResources().get(0).getId()));

        // Domain 삭제
        String domainResult = domainsServiceV3.delete(domainLists.getResources().get(0).getId());
        Assert.assertTrue(domainResult.contains(domainLists.getResources().get(0).getId()));

        // App 삭제
        List<String> appNames = new ArrayList<>();
        appNames.add("test-app");

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        String appResult = appServiceV3.delete(appLists.getResources().get(0).getId());
        Assert.assertTrue(appResult.contains(appLists.getResources().get(0).getId()));

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