package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Lifecycle;
import org.cloudfoundry.client.v3.LifecycleType;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.builds.*;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Builds Service 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BuildsServiceTest {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @Mock
    private Builds builds;

    @InjectMocks
    private BuildsService buildsService;

    @BeforeEach
    void setUp() {
        buildsService = spy(new BuildsService());
        doReturn(tokenProvider).when(buildsService).tokenProvider();
        doReturn(cloudFoundryClient).when(buildsService).cloudFoundryClient(any(TokenProvider.class));
        when(cloudFoundryClient.builds()).thenReturn(builds);
    }

    @Test
    @DisplayName("빌드 생성 테스트")
    void create() {
        String packageGuid = "test-package-guid";
        CreateBuildResponse expectedResponse = CreateBuildResponse.builder()
                .id("test-id")
                .createdAt(Instant.now().toString())
                .createdBy(CreatedBy.builder().id("test-user-id").name("test-user").build())
                .state(BuildState.STAGING)
                .inputPackage(Relationship.builder().id(packageGuid).build())
                .lifecycle(Lifecycle.builder().type(LifecycleType.KPACK).build())
                .build();
        when(builds.create(any(CreateBuildRequest.class))).thenReturn(Mono.just(expectedResponse));

        CreateBuildResponse actualResponse = buildsService.create(packageGuid);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(builds).create(argThat(request ->
                request.getPackage().getId().equals(packageGuid)
        ));
    }

    @Test
    @DisplayName("토큰 제공자를 사용한 빌드 생성 테스트")
    void createWithTokenProvider() {
        String packageGuid = "test-package-guid";
        CreateBuildResponse expectedResponse = CreateBuildResponse.builder()
                .id("test-id")
                .createdAt(Instant.now().toString())
                .createdBy(CreatedBy.builder().id("test-user-id").name("test-user").build())
                .state(BuildState.STAGING)
                .inputPackage(Relationship.builder().id(packageGuid).build())
                .lifecycle(Lifecycle.builder().type(LifecycleType.KPACK).build())
                .build();
        when(builds.create(any(CreateBuildRequest.class))).thenReturn(Mono.just(expectedResponse));

        CreateBuildResponse actualResponse = buildsService.create(packageGuid, tokenProvider);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(builds).create(argThat(request ->
                request.getPackage().getId().equals(packageGuid)
        ));
    }

    @Test
    @DisplayName("빌드 생성 중 예외 처리 테스트")
    void createShouldHandleException() {
        when(builds.create(any(CreateBuildRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Test exception")));

        assertThrows(RuntimeException.class, () -> buildsService.create("test-guid"));
    }

    @Test
    @DisplayName("빌드 조회 테스트")
    void get() {
        String guid = "test-guid";
        GetBuildResponse expectedResponse = GetBuildResponse.builder()
                .id("test-id")
                .createdAt(Instant.now().toString())
                .createdBy(CreatedBy.builder().id("test-user-id").name("test-user").build())
                .state(BuildState.STAGED)
                .inputPackage(Relationship.builder().id(guid).build())
                .lifecycle(Lifecycle.builder().type(LifecycleType.KPACK).build())
                .build();
        when(builds.get(any(GetBuildRequest.class))).thenReturn(Mono.just(expectedResponse));

        GetBuildResponse actualResponse = buildsService.get(guid);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(builds).get(argThat(request ->
                request.getBuildId().equals(guid)
        ));
    }

    @Test
    @DisplayName("토큰 제공자를 사용한 빌드 조회 테스트")
    void getWithTokenProvider() {
        String guid = "test-guid";
        GetBuildResponse expectedResponse = GetBuildResponse.builder()
                .id("test-id")
                .createdAt(Instant.now().toString())
                .createdBy(CreatedBy.builder().id("test-user-id").name("test-user").build())
                .state(BuildState.STAGED)
                .inputPackage(Relationship.builder().id(guid).build())
                .lifecycle(Lifecycle.builder().type(LifecycleType.KPACK).build())
                .build();
        when(builds.get(any(GetBuildRequest.class))).thenReturn(Mono.just(expectedResponse));

        GetBuildResponse actualResponse = buildsService.get(guid, tokenProvider);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(builds).get(argThat(request ->
                request.getBuildId().equals(guid)
        ));
    }

    @Test
    @DisplayName("빌드 목록 조회 테스트")
    void list() {
        String guid = "test-guid";
        ListBuildsResponse expectedResponse = ListBuildsResponse.builder()
                .build();
        when(builds.list(any(ListBuildsRequest.class))).thenReturn(Mono.just(expectedResponse));

        ListBuildsResponse actualResponse = buildsService.list(guid);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(builds).list(any(ListBuildsRequest.class));
    }
}