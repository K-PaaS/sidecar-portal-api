package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.serviceinstances.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  ServiceInstances Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceInstancesServiceV3 테스트")
class ServiceInstancesServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Mock
    private ServiceInstancesV3 serviceInstancesV3;
    @InjectMocks
    private ServiceInstancesServiceV3 serviceInstancesServiceV3;

    private static final String TEST_NAME = "test-service-instance";
    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ID = "service-instance-id";

    @BeforeEach
    void setUp() {
        serviceInstancesServiceV3 = spy(new ServiceInstancesServiceV3());
        doReturn(tokenProvider).when(serviceInstancesServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(serviceInstancesServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.serviceInstancesV3()).thenReturn(serviceInstancesV3);
    }

    @Test
    @DisplayName("서비스 인스턴스 생성 테스트")
    void create() {
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setName("test-instance");
        serviceInstance.setType(ServiceInstanceType.MANAGED);
        CreateServiceInstanceResponse expectedResponse = CreateServiceInstanceResponse.builder().build();
        when(serviceInstancesV3.create(any())).thenReturn(Mono.just(expectedResponse));
        CreateServiceInstanceResponse actualResponse = serviceInstancesServiceV3.create(serviceInstance);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).create(any());
    }

    @Test
    @DisplayName("서비스 인스턴스 삭제 테스트")
    void delete() {
        String guid = "test-guid";
        when(serviceInstancesV3.delete(any())).thenReturn(Mono.just(Optional.of("deleted")));
        Optional<String> result = serviceInstancesServiceV3.delete(guid);
        assertTrue(result.isPresent());
        assertEquals("deleted", result.get());
        verify(serviceInstancesV3).delete(any());
    }

    @Test
    @DisplayName("서비스 인스턴스 조회 테스트")
    void get() {
        String guid = "test-guid";
        GetServiceInstanceResponse expectedResponse = GetServiceInstanceResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .name(TEST_NAME)
                .build();
        when(serviceInstancesV3.get(any())).thenReturn(Mono.just(expectedResponse));
        GetServiceInstanceResponse actualResponse = serviceInstancesServiceV3.get(guid);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).get(any());
    }

    @Test
    @DisplayName("관리형 서비스 파라미터 조회 테스트")
    void getManagedServiceParameters() {
        String guid = "test-guid";
        GetManagedServiceParametersResponse expectedResponse = GetManagedServiceParametersResponse.builder().build();
        when(serviceInstancesV3.getManagedServiceParameters(any())).thenReturn(Mono.just(expectedResponse));
        GetManagedServiceParametersResponse actualResponse = serviceInstancesServiceV3.getManagedServiceParameters(guid);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).getManagedServiceParameters(any());
    }

    @Test
    @DisplayName("사용자 제공 자격 증명 조회 테스트")
    void getUserProvidedCredentials() {
        String guid = "test-guid";
        GetUserProvidedCredentialsResponse expectedResponse = GetUserProvidedCredentialsResponse.builder().build();
        when(serviceInstancesV3.getUserProvidedCredentials(any())).thenReturn(Mono.just(expectedResponse));
        GetUserProvidedCredentialsResponse actualResponse = serviceInstancesServiceV3.getUserProvidedCredentials(guid);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).getUserProvidedCredentials(any());
    }

    @Test
    @DisplayName("서비스 인스턴스 목록 조회 테스트")
    void list() {
        List<String> spaceGuid = Arrays.asList("space-guid-1", "space-guid-2");
        List<String> serviceInstanceNames = Arrays.asList("instance-1", "instance-2");
        ListServiceInstancesResponse expectedResponse = ListServiceInstancesResponse.builder().build();
        when(serviceInstancesV3.list(any())).thenReturn(Mono.just(expectedResponse));
        ListServiceInstancesResponse actualResponse = serviceInstancesServiceV3.list(spaceGuid, serviceInstanceNames);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).list(any());
    }

    @Test
    @DisplayName("공유된 공간 관계 목록 조회 테스트")
    void listSharedSpacesRelationship() {
        String guid = "test-guid";
        ListSharedSpacesRelationshipResponse expectedResponse = ListSharedSpacesRelationshipResponse.builder().build();
        when(serviceInstancesV3.listSharedSpacesRelationship(any())).thenReturn(Mono.just(expectedResponse));
        ListSharedSpacesRelationshipResponse actualResponse = serviceInstancesServiceV3.listSharedSpacesRelationship(guid);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).listSharedSpacesRelationship(any());
    }

    @Test
    @DisplayName("서비스 인스턴스 공유 테스트")
    void share() {
        String guid = "test-guid";
        ShareServiceInstanceResponse expectedResponse = ShareServiceInstanceResponse.builder().build();
        when(serviceInstancesV3.share(any())).thenReturn(Mono.just(expectedResponse));
        ShareServiceInstanceResponse actualResponse = serviceInstancesServiceV3.share(guid);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).share(any());
    }

    @Test
    @DisplayName("서비스 인스턴스 공유 해제 테스트")
    void unshare() {
        String guid = "test-guid";
        when(serviceInstancesV3.unshare(any())).thenReturn(Mono.empty());
        Void result = serviceInstancesServiceV3.unshare(guid);
        assertNull(result);
        verify(serviceInstancesV3).unshare(any());
    }

    @Test
    @DisplayName("서비스 인스턴스 업데이트 테스트")
    void update() {
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setId("test-id");
        UpdateServiceInstanceResponse expectedResponse = UpdateServiceInstanceResponse.builder().build();
        when(serviceInstancesV3.update(any())).thenReturn(Mono.just(expectedResponse));
        UpdateServiceInstanceResponse actualResponse = serviceInstancesServiceV3.update(serviceInstance);
        assertEquals(expectedResponse, actualResponse);
        verify(serviceInstancesV3).update(any());
    }
}