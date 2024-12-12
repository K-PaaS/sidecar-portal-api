package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.*;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.applications.CreateApplicationResponse;
import org.cloudfoundry.client.v3.applications.GetApplicationResponse;
import org.cloudfoundry.client.v3.applications.ListApplicationsResponse;
import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.cloudfoundry.client.v3.builds.GetBuildResponse;
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
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 *  Builds Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("BuildsService Test")
public class BuildsServiceTest {
    @InjectMocks
    BuildsService buildsService;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
    @InjectMocks
    ApplicationsServiceV3 appServiceV3;
    @InjectMocks
    PackagesService packagesService;
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

    private static MultipartFile multipartFile = null;

    @Before
    public void setUp() throws IOException {

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("build-org");

        // Space 객체 생성
        space = new Space();
        space.setName("build-space");

        // App 객체 생성
        app = new Application();
        app.setName("build-app");

        // File 객체 (프로젝트 내부에 있는 샘플 파일로 설정)
        // File 객체 -> Multipartfile 객체

        File file = new File ("src/test/java/org/kpaas/sidecar/portal/api/service/server.zip");
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

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Build")
    public void test1_createBuild() throws Exception {
        // Organization Create
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceResult = spacesServiceV3.create(space);
        spaceGuid = spaceResult.getId();

        // App Create
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateApplicationResponse appResult = appServiceV3.create(app);
        Assert.assertNotNull(appResult.getId());

        // Package Create
        CreatePackageResponse packageResult = packagesService.create(appResult.getId());
        packageGuid = packageResult.getId();
        Assert.assertNotNull(packageResult.getId());

        // Upload Package
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

        // 서비스 호출
        UploadPackageResponse result = packagesService.upload(packageGuid,  multipartFile);
        Assert.assertNotNull(result.getType());

        // Build Create
        CreateBuildResponse buildResult = buildsService.create(packageGuid);
        String buildGuid = buildResult.getId();

        // Get Build
        GetBuildResponse buildGet = buildsService.get(buildGuid);
        Assert.assertNotNull(buildGet.getId());
    }

    @Test
    @DisplayName("Get Build")
    public void test2_getBuild() throws InterruptedException {
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