package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.applications.CreateApplicationResponse;
import org.cloudfoundry.client.v3.applications.GetApplicationResponse;
import org.cloudfoundry.client.v3.applications.ListApplicationsResponse;
import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.builds.GetBuildResponse;
import org.cloudfoundry.client.v3.droplets.GetDropletResponse;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.packages.*;
import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.cloudfoundry.client.v3.spaces.SpaceRelationships;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.model.Organization;
import org.kpaas.sidecar.portal.api.model.Space;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 *  Package Service 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("PackagesService Test")
public class PackagesServiceTest {
    @InjectMocks
    PackagesService packagesService;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
    @InjectMocks
    ApplicationsServiceV3 appServiceV3;
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

    private static Organization organization;
    private static Space space;
    private static Application app;

    private static String orgGuid = null;
    private static String spaceGuid = null;
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
        organization.setName("packages-org");

        // Space 객체 생성
        space = new Space();
        space.setName("packages-space");

        // App 객체 생성
        app = new Application();
        app.setName("packages-app");

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

        appServiceV3.apiHost = apiHost;
        appServiceV3.tokenKind = tokenKind;

        packagesService.apiHost = apiHost;
        packagesService.tokenKind = tokenKind;

        buildsService.apiHost = apiHost;
        buildsService.tokenKind = tokenKind;

        dropletsService.apiHost = apiHost;
        dropletsService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Package")
    public void test1_createPackage() throws Exception {
        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceResult = spacesServiceV3.create(space);
        spaceGuid = spaceResult.getId();

        // App 생성
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateApplicationResponse appResult = appServiceV3.create(app);
        appGuid = appResult.getId();
        Assert.assertNotNull(appResult.getId());

        // Package 생성
        CreatePackageResponse result = packagesService.create(appGuid);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Upload Package")
    public void test2_uploadPackage() throws Exception {
        // App Name 정보 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListPackagesResponse lists = packagesService.list(appGuids, null, null);

        GetPackageResponse packageGet = packagesService.get(lists.getResources().get(0).getId());
        packageGuid = packageGet.getId();

        // Upload Package
        UploadPackageResponse packageUpload = packagesService.upload(packageGuid,  multipartFile);
        Assert.assertNotNull(packageUpload.getType());
    }

    @Test
    @DisplayName("Get Package")
    public void test3_getPackage() throws Exception {
        // App name 정보 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        // App List 가져오기
        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        // App 조회정보 가져오기
        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListPackagesResponse lists = packagesService.list(appGuids, null, null);

        GetPackageResponse result = packagesService.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("List Packages")
    public void test4_listPackages() throws Exception {
        // App name 정보 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        // App List 가져오기
        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        // App 조회정보 가져오기
        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListPackagesResponse lists = packagesService.list(appGuids, null, null);
        Assert.assertTrue(lists.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("List Package Droplets")
    public void test5_listPackageDroplets() throws Exception {
        // App name 정보 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        // App List 가져오기
        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        // App 조회정보 가져오기
        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListPackagesResponse lists = packagesService.list(appGuids, null, null);
        GetPackageResponse packageGet = packagesService.get(lists.getResources().get(0).getId());
        String packageGuid = packageGet.getId();

        // Build Create
        CreateBuildResponse buildCreate = buildsService.create(packageGuid);
        String buildGuid = buildCreate.getId();
        Assert.assertNotNull(buildGuid);

        // 빌드 상태 확인
        long start = System.currentTimeMillis();
        long end = start + BUILD_INTERVAL_SECOND * 2000;

        GetBuildResponse buildGet = null;
        while (true) {
            buildGet = buildsService.get(buildGuid);
            if (buildGet.getState().getValue().equals("STAGED")) {
                break;
            }
            if (System.currentTimeMillis() > end) {
                Assert.fail("App Build Time Over");
            }
            Thread.sleep(2000);
        }

        Assert.assertNotNull(buildGet);
        Assert.assertEquals("STAGED", buildGet.getState().getValue());

        dropletGuid = buildGet.getDroplet().getId();
        Assert.assertNotNull(dropletGuid);

        GetDropletResponse dropletGet = dropletsService.get(dropletGuid);
        Assert.assertNotNull(dropletGet);
        Assert.assertEquals(dropletGuid, dropletGet.getId());

        // Package Droplets List
        ListPackageDropletsResponse dropletsLists = packagesService.listDroplets(packageGuid, null);
        Assert.assertTrue(dropletsLists.getPagination().getTotalResults() > 0);

        // App 삭제
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