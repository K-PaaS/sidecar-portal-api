package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.packages.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import java.io.*;
import java.nio.file.Files;

import static org.mockito.Mockito.when;

/**
 *  Package Service 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PackagesServiceTest {
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

    String appGuids;
    String packageGuids;
    String dropletGuids;
    private static MultipartFile multipartFile;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        // 순서
        // < setup 에서 진행>
        // File 객체 (프로젝트 내부에 있는 샘플 파일로 설정)
        // File 객체 -> Multipartfile 객체

        File file = new File("src/test/java/org/kpaas/sidecar/portal/api/service/server.zip");
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }

        String mimeType = Files.probeContentType(file.toPath());
        FileItem fileItem = new DiskFileItem("file", mimeType, false, file.getName(), (int) file.length(), file.getParentFile());

        try (InputStream input = new FileInputStream(file);
             OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to create multipart file", ex);
        }

        multipartFile = new CommonsMultipartFile(fileItem);

        appGuids = "33f29649-7bee-40ae-a6c9-d0197334c2c4";
        packageGuids = "83656bbb-c9e0-4026-9538-e3b6393f7526";
        dropletGuids = "796156ca-70d8-4bd0-ad61-ac2d361271b6";

        params = new Params();
        params.setClusterToken(adminToken);

        packagesService.apiHost = apiHost;
        packagesService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Package")
    public void test1_createPackage() throws Exception {
        CreatePackageResponse result = packagesService.create(appGuids);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Package")
    public void test2_getPackage() throws Exception {
        ListPackagesResponse lists = packagesService.list(null, null, null);

        GetPackageResponse result = packagesService.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("List Packages")
    public void test3_listPackages() throws Exception {
        ListPackagesResponse result = packagesService.list(null, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("List Package Droplets")
    public void test4_listPackageDroplets() throws Exception {
        ListPackageDropletsResponse result = packagesService.listDroplets(packageGuids, null);
        Assert.assertNotNull(result);
    }

//    @Test
//    @DisplayName("Upload Package")
//    public void test5_uploadPackage() throws Exception {
//        // 서비스 호출
//        UploadPackageResponse result = packagesService.upload(packageGuids,  multipartFile);
//        Assert.assertNotNull(result.getType());
//    }
}