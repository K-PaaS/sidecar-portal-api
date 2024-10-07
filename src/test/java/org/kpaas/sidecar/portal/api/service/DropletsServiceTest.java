package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Lifecycle;
import org.cloudfoundry.client.v3.LifecycleType;
import org.cloudfoundry.client.v3.droplets.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 *  Droplets Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("Droplets Service 테스트")
public class DropletsServiceTest {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @Mock
    private Droplets droplets;

    @InjectMocks
    private DropletsService dropletsService;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ID = "droplets-service-id";

    @BeforeEach
    void setUp() {
        dropletsService = spy(new DropletsService());
        doReturn(tokenProvider).when(dropletsService).tokenProvider();
        doReturn(cloudFoundryClient).when(dropletsService).cloudFoundryClient(any());
        when(cloudFoundryClient.droplets()).thenReturn(droplets);
    }

    @Test
    @DisplayName("Droplet 복사 기능 테스트")
    void copy() {
        CopyDropletResponse expectedResponse = CopyDropletResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .executionMetadata("executionMetadata")
                .lifecycle(Lifecycle.builder()
                        .type(LifecycleType.BUILDPACK)
                        .build())
                .state(DropletState.STAGED)
                .build();
        when(droplets.copy(any(CopyDropletRequest.class))).thenReturn(Mono.just(expectedResponse));

        CopyDropletResponse actualResponse = dropletsService.copy("guid");

        assertEquals(expectedResponse, actualResponse);
        verify(droplets).copy(any(CopyDropletRequest.class));
    }

    @Test
    @DisplayName("Droplet 삭제 기능 테스트")
    void delete() {
        String expectedResponse = "Deleted";
        when(droplets.delete(any(DeleteDropletRequest.class))).thenReturn(Mono.just(expectedResponse));

        String actualResponse = dropletsService.delete("guid");

        assertEquals(expectedResponse, actualResponse);
        verify(droplets).delete(any(DeleteDropletRequest.class));
    }

    @Test
    @DisplayName("특정 Droplet 조회 기능 테스트")
    void get() {
        GetDropletResponse expectedResponse = GetDropletResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .executionMetadata("executionMetadata")
                .lifecycle(Lifecycle.builder()
                        .type(LifecycleType.BUILDPACK)
                        .build())
                .state(DropletState.STAGED)
                .build();
        when(droplets.get(any(GetDropletRequest.class))).thenReturn(Mono.just(expectedResponse));

        GetDropletResponse actualResponse = dropletsService.get("guid");

        assertEquals(expectedResponse, actualResponse);
        verify(droplets).get(any(GetDropletRequest.class));
    }

    @Test
    @DisplayName("Droplet 목록 조회 기능 테스트")
    void list() {
        ListDropletsResponse expectedResponse = ListDropletsResponse.builder().build();
        when(droplets.list(any(ListDropletsRequest.class))).thenReturn(Mono.just(expectedResponse));

        ListDropletsResponse actualResponse = dropletsService.list("guid");

        assertEquals(expectedResponse, actualResponse);
        verify(droplets).list(any(ListDropletsRequest.class));
    }
}