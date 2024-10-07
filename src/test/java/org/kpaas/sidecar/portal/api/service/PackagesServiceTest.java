package org.kpaas.sidecar.portal.api.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.cloudfoundry.client.v3.packages.*;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  Packages Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("PackagesService 테스트")
class PackagesServiceTest {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Mock
    private Packages packages;
    @InjectMocks
    private PackagesService packagesService;

    @BeforeEach
    void setUp() {
        packagesService = spy(new PackagesService());
        doReturn(tokenProvider).when(packagesService).tokenProvider();
        doReturn(cloudFoundryClient).when(packagesService).cloudFoundryClient(any());
        when(cloudFoundryClient.packages()).thenReturn(packages);
    }

    @Test
    @DisplayName("패키지 복사 기능 테스트")
    void copy() {
        String packageGuid = "pkg-guid";
        String appGuid = "app-guid";
        CopyPackageResponse mockResponse = mock(CopyPackageResponse.class);
        when(packages.copy(any())).thenReturn(Mono.just(mockResponse));

        CopyPackageResponse response = packagesService.copy(packageGuid, appGuid);

        assertEquals(mockResponse, response);
        verify(packages).copy(any());
    }

    @Test
    @DisplayName("새 패키지 생성 기능 테스트")
    void create() {
        String guid = "app-guid";
        CreatePackageResponse mockResponse = mock(CreatePackageResponse.class);
        when(packages.create(any())).thenReturn(Mono.just(mockResponse));

        CreatePackageResponse response = packagesService.create(guid);

        assertEquals(mockResponse, response);
        verify(packages).create(any());
    }

    @Test
    @DisplayName("패키지 삭제 기능 테스트")
    void delete() {
        String guid = "pkg-guid";
        when(packages.delete(any())).thenReturn(Mono.just(guid));

        String response = packagesService.delete(guid);

        assertEquals(guid, response);
        verify(packages).delete(any());
    }

    @Test
    @DisplayName("패키지 다운로드 기능 테스트")
    void download() {
        String guid = "pkg-guid";
        Flux<byte[]> mockFlux = Flux.just(new byte[]{1, 2, 3});
        when(packages.download(any())).thenReturn(mockFlux);

        Flux<byte[]> response = packagesService.download(guid);

        assertNotNull(response);
        verify(packages).download(any());
    }

    @Test
    @DisplayName("패키지 정보 조회 기능 테스트")
    void get() {
        String guid = "pkg-guid";
        GetPackageResponse mockResponse = mock(GetPackageResponse.class);
        when(packages.get(any())).thenReturn(Mono.just(mockResponse));

        GetPackageResponse response = packagesService.get(guid);

        assertEquals(mockResponse, response);
        verify(packages).get(any());
    }

    @Test
    @DisplayName("패키지 목록 조회 기능 테스트")
    void list() {
        List<String> appGuids = Collections.singletonList("app-guid");
        List<String> orgGuids = Collections.singletonList("org-guid");
        List<String> spaceGuids = Collections.singletonList("space-guid");
        ListPackagesResponse mockResponse = mock(ListPackagesResponse.class);
        when(packages.list(any())).thenReturn(Mono.just(mockResponse));

        ListPackagesResponse response = packagesService.list(appGuids, orgGuids, spaceGuids);

        assertEquals(mockResponse, response);
        verify(packages).list(any());
    }

    @Test
    @DisplayName("패키지의 드롭릿 목록 조회 기능 테스트")
    void listDroplets() {
        String packageGuid = "pkg-guid";
        List<String> dropletGuids = Arrays.asList("droplet1", "droplet2");
        ListPackageDropletsResponse mockResponse = mock(ListPackageDropletsResponse.class);
        when(packages.listDroplets(any())).thenReturn(Mono.just(mockResponse));

        ListPackageDropletsResponse response = packagesService.listDroplets(packageGuid, dropletGuids);

        assertEquals(mockResponse, response);
        verify(packages).listDroplets(any());
    }

    @Test
    @DisplayName("패키지 업로드 기능 테스트")
    void upload() throws IOException {
        String guid = "pkg-guid";
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.zip");
        doNothing().when(mockFile).transferTo(any(File.class));
        UploadPackageResponse mockResponse = mock(UploadPackageResponse.class);
        when(packages.upload(any())).thenReturn(Mono.just(mockResponse));

        UploadPackageResponse response = packagesService.upload(guid, mockFile);

        assertNotNull(response);
        verify(packages).upload(any());
    }
}