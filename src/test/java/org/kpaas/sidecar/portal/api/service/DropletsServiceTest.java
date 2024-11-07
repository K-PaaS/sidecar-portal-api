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
import org.cloudfoundry.client.v3.packages.CreatePackageResponse;
import org.cloudfoundry.client.v3.packages.GetPackageResponse;
import org.cloudfoundry.client.v3.packages.ListPackagesResponse;
import org.cloudfoundry.client.v3.packages.UploadPackageResponse;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 *  droplets Service 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("DropletsService Test")
public class DropletsServiceTest {
    @InjectMocks
    DropletsService dropletsService;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
    @InjectMocks
    ApplicationsServiceV3 appServiceV3;
    @InjectMocks
    PackagesService packagesService;
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

    private static Organization organization = null;
    private static Space space = null;
    private static Application app = null;

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
    public void setUp() throws InterruptedException, IOException {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("droplets-org");

        // Space 객체 생성
        space = new Space();
        space.setName("droplets-space");

        // App 객체 생성
        app = new Application();
        app.setName("droplets-app");

        // File 객체 ( 프로젝트 내부에 있는 샘플 파일로 설정)
        // File 객체 ->  Multipartfile 객체

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

        // Organization Create
        CreateOrganizationResponse orgCreate = organizationsServiceV3.create(organization);
        orgGuid = orgCreate.getId();

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceCreate = spacesServiceV3.create(space);
        spaceGuid = spaceCreate.getId();

        // App Create
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateApplicationResponse appCreate = appServiceV3.create(app);
        Assert.assertNotNull(appCreate.getId());

        // Package Create
        CreatePackageResponse packageCreate = packagesService.create(appCreate.getId());
        packageGuid = packageCreate.getId();
        Assert.assertNotNull(packageCreate.getId());

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
    @DisplayName("Get Droplets")
    public void test1_getDroplets() throws Exception {
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

        // App 삭제
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

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