package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.buildpacks.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  BuildPacks Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("BuildpacksServiceV3 테스트")
public class BuildpacksServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @Mock
    private BuildpacksV3 buildpacksV3;

    @InjectMocks
    private BuildpacksServiceV3 buildpacksServiceV3;

    @BeforeEach
    void setUp() {
        buildpacksServiceV3 = spy(new BuildpacksServiceV3());
        doReturn(tokenProvider).when(buildpacksServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(buildpacksServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.buildpacksV3()).thenReturn(buildpacksV3);
    }

    @Test
    @DisplayName("빌드팩 목록 조회 테스트")
    void list() {
        ListBuildpacksResponse mockResponse = mock(ListBuildpacksResponse.class);
        when(buildpacksV3.list(any(ListBuildpacksRequest.class))).thenReturn(Mono.just(mockResponse));

        ListBuildpacksResponse result = buildpacksServiceV3.list();

        assertNotNull(result);
        verify(buildpacksV3).list(any(ListBuildpacksRequest.class));
    }

    @Test
    @DisplayName("빌드팩 생성 테스트")
    void create() {
        String guid = "test-guid";
        CreateBuildpackResponse mockResponse = mock(CreateBuildpackResponse.class);
        when(buildpacksV3.create(any(CreateBuildpackRequest.class))).thenReturn(Mono.just(mockResponse));

        CreateBuildpackResponse result = buildpacksServiceV3.create(guid);

        assertNotNull(result);
        verify(buildpacksV3).create(any(CreateBuildpackRequest.class));
    }

    @Test
    @DisplayName("빌드팩 삭제 테스트")
    void delete() {
        String guid = "test-guid";
        when(buildpacksV3.delete(any(DeleteBuildpackRequest.class))).thenReturn(Mono.empty());

        String result = buildpacksServiceV3.delete(guid);

        assertNull(result);
        verify(buildpacksV3).delete(any(DeleteBuildpackRequest.class));
    }

    @Test
    @DisplayName("빌드팩 조회 테스트")
    void get() {
        String guid = "test-guid";
        GetBuildpackResponse mockResponse = mock(GetBuildpackResponse.class);
        when(buildpacksV3.get(any(GetBuildpackRequest.class))).thenReturn(Mono.just(mockResponse));

        GetBuildpackResponse result = buildpacksServiceV3.get(guid);

        assertNotNull(result);
        verify(buildpacksV3).get(any(GetBuildpackRequest.class));
    }

    @Test
    @DisplayName("빌드팩 업데이트 테스트")
    void update() {
        String guid = "test-guid";
        UpdateBuildpackResponse mockResponse = mock(UpdateBuildpackResponse.class);
        when(buildpacksV3.update(any(UpdateBuildpackRequest.class))).thenReturn(Mono.just(mockResponse));

        UpdateBuildpackResponse result = buildpacksServiceV3.update(guid);

        assertNotNull(result);
        verify(buildpacksV3).update(any(UpdateBuildpackRequest.class));
    }

    @Test
    @DisplayName("빌드팩 업로드 테스트")
    void upload() {
        String guid = "test-guid";
        UploadBuildpackResponse mockResponse = mock(UploadBuildpackResponse.class);
        when(buildpacksV3.upload(any(UploadBuildpackRequest.class))).thenReturn(Mono.just(mockResponse));

        UploadBuildpackResponse result = buildpacksServiceV3.upload(guid);

        assertNotNull(result);
        verify(buildpacksV3).upload(any(UploadBuildpackRequest.class));
    }
}