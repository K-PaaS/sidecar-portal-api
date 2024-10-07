package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.deployments.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Deployments Service V3 테스트 클래스
 *
 * @author woogie
 * @version 1.1
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("DeploymentsServiceV3 테스트")
class DeploymentsServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @Mock
    private DeploymentsV3 deploymentsV3;

    @InjectMocks
    private DeploymentsServiceV3 deploymentsServiceV3;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ID = "deployments-service-id";
    private static final String TEST_GUID = "test-guid";

    @BeforeEach
    void setUp() {
        deploymentsServiceV3 = spy(new DeploymentsServiceV3());
        doReturn(tokenProvider).when(deploymentsServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(deploymentsServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.deploymentsV3()).thenReturn(deploymentsV3);
    }

    @Nested
    @DisplayName("cancel 메소드")
    class CancelTest {
        @Test
        @DisplayName("배포 취소 요청 성공")
        void cancelDeploymentSuccess() {
            CancelDeploymentResponse expectedResponse = CancelDeploymentResponse.builder().build();
            when(deploymentsV3.cancel(any(CancelDeploymentRequest.class))).thenReturn(Mono.just(expectedResponse));

            CancelDeploymentResponse actualResponse = deploymentsServiceV3.cancel(TEST_GUID);

            assertEquals(expectedResponse, actualResponse);
            verify(deploymentsV3).cancel(argThat(request ->
                    request.getDeploymentId().equals(TEST_GUID)
            ));
        }
    }

    @Nested
    @DisplayName("create 메소드")
    class CreateTest {
        @Test
        @DisplayName("새 배포 생성 성공")
        void createDeploymentSuccess() {
            CreateDeploymentResponse expectedResponse = CreateDeploymentResponse.builder()
                    .createdAt(TEST_CREATED_AT)
                    .id(TEST_ID)
                    .build();
            when(deploymentsV3.create(any(CreateDeploymentRequest.class))).thenReturn(Mono.just(expectedResponse));

            CreateDeploymentResponse actualResponse = deploymentsServiceV3.create("any-guid");

            assertEquals(expectedResponse, actualResponse);
            verify(deploymentsV3).create(any(CreateDeploymentRequest.class));
        }
    }

    @Nested
    @DisplayName("get 메소드")
    class GetTest {
        @Test
        @DisplayName("특정 배포 정보 조회 성공")
        void getDeploymentSuccess() {
            GetDeploymentResponse expectedResponse = GetDeploymentResponse.builder()
                    .createdAt(TEST_CREATED_AT)
                    .id(TEST_ID)
                    .build();
            when(deploymentsV3.get(any(GetDeploymentRequest.class))).thenReturn(Mono.just(expectedResponse));

            GetDeploymentResponse actualResponse = deploymentsServiceV3.get(TEST_GUID);

            assertEquals(expectedResponse, actualResponse);
            verify(deploymentsV3).get(argThat(request ->
                    request.getDeploymentId().equals(TEST_GUID)
            ));
        }
    }

    @Nested
    @DisplayName("list 메소드")
    class ListTest {
        @Test
        @DisplayName("모든 배포 목록 조회 성공")
        void listDeploymentsSuccess() {
            ListDeploymentsResponse expectedResponse = ListDeploymentsResponse.builder().build();
            when(deploymentsV3.list(any(ListDeploymentsRequest.class))).thenReturn(Mono.just(expectedResponse));

            ListDeploymentsResponse actualResponse = deploymentsServiceV3.list();

            assertEquals(expectedResponse, actualResponse);
            verify(deploymentsV3).list(any(ListDeploymentsRequest.class));
        }
    }
}