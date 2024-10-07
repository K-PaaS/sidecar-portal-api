package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Checksum;
import org.cloudfoundry.client.v3.ChecksumType;
import org.cloudfoundry.client.v3.resourcematch.ListMatchingResourcesRequest;
import org.cloudfoundry.client.v3.resourcematch.ListMatchingResourcesResponse;
import org.cloudfoundry.client.v3.resourcematch.MatchedResource;
import org.cloudfoundry.client.v3.resourcematch.ResourceMatchV3;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ResourceMatch Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("ResourceMatchServiceV3 테스트")
public class ResourceMatchServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @Mock
    private ResourceMatchV3 resourceMatchV3;

    @InjectMocks
    private ResourceMatchServiceV3 resourceMatchServiceV3;

    @BeforeEach
    void setUp() {
        resourceMatchServiceV3 = spy(new ResourceMatchServiceV3());
        doReturn(tokenProvider).when(resourceMatchServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(resourceMatchServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.resourceMatchV3()).thenReturn(resourceMatchV3);
    }

    @Nested
    @DisplayName("list 메소드")
    class ListMethod {

        @Test
        @DisplayName("매칭되는 리소스 목록을 성공적으로 반환해야 한다")
        void shouldReturnMatchingResourcesListSuccessfully() {
            // Given
            ListMatchingResourcesResponse expectedResponse = ListMatchingResourcesResponse.builder()
                    .resource(MatchedResource.builder()
                            .checksum(Checksum.builder()
                                    .type(ChecksumType.SHA256)
                                    .value("test")
                                    .build())
                            .size(1)
                            .build())
                    .build();

            when(resourceMatchV3.list(any(ListMatchingResourcesRequest.class)))
                    .thenReturn(Mono.just(expectedResponse));

            // When
            ListMatchingResourcesResponse actualResponse = resourceMatchServiceV3.list();

            // Then
            assertNotNull(actualResponse, "응답은 null이 아니어야 합니다");
            assertEquals(expectedResponse, actualResponse, "예상 응답과 실제 응답이 일치해야 합니다");

            verify(cloudFoundryClient, times(1)).resourceMatchV3();
            verify(resourceMatchV3, times(1)).list(any(ListMatchingResourcesRequest.class));
        }

        @Test
        @DisplayName("예외 발생 시 적절히 처리해야 한다")
        void shouldHandleExceptionAppropriately() {
            // Given
            when(resourceMatchV3.list(any(ListMatchingResourcesRequest.class)))
                    .thenReturn(Mono.error(new RuntimeException("API 호출 실패")));

            // When & Then
            assertThrows(RuntimeException.class, () -> resourceMatchServiceV3.list(),
                    "RuntimeException이 발생해야 합니다");

            verify(cloudFoundryClient, times(1)).resourceMatchV3();
            verify(resourceMatchV3, times(1)).list(any(ListMatchingResourcesRequest.class));
        }
    }
}