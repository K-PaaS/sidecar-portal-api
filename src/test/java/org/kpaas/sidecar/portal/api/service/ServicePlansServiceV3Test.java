package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.serviceplans.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *  ServicePlans Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("ServicePlansServiceV3 테스트")
class ServicePlansServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Mock
    private ServicePlansV3 servicePlansV3;
    @InjectMocks
    private ServicePlansServiceV3 servicePlansServiceV3;

    private static final String TEST_GUID = "test-guid";
    private static final String TEST_NAME = "test-serviceplansService";
    private static final String TEST_DESCRIPTION = "test-serviceplansService";
    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ID = "service-id";
    private static final String TEST_BROKER_CATALOG_ID = "broker-catalog-id";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicePlansServiceV3 = spy(new ServicePlansServiceV3());
        doReturn(cloudFoundryClient).when(servicePlansServiceV3).cloudFoundryClient(any());
        doReturn(tokenProvider).when(servicePlansServiceV3).tokenProvider();
        when(cloudFoundryClient.servicePlansV3()).thenReturn(servicePlansV3);
    }

    @Test
    @DisplayName("서비스 플랜 삭제 성공 테스트")
    void delete_Success() {
        when(servicePlansV3.delete(any())).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> servicePlansServiceV3.delete(TEST_GUID));

        verify(servicePlansV3).delete(argThat(request ->
                request.getServicePlanId().equals(TEST_GUID)
        ));
    }

    @Test
    @DisplayName("서비스 플랜 삭제 실패 테스트")
    void delete_Failure() {
        when(servicePlansV3.delete(any())).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        assertThrows(RuntimeException.class, () -> servicePlansServiceV3.delete(TEST_GUID));

        verify(servicePlansV3).delete(any());
    }

    @Test
    @DisplayName("서비스 플랜 조회 성공 테스트")
    void get_Success() {
        GetServicePlanResponse mockResponse = createMockGetServicePlanResponse();
        when(servicePlansV3.get(any())).thenReturn(Mono.just(mockResponse));

        GetServicePlanResponse result = servicePlansServiceV3.get(TEST_GUID);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_DESCRIPTION, result.getDescription());
        assertTrue(result.getFree());
        assertEquals(Visibility.ADMIN, result.getVisibilityType());
        assertEquals(TEST_CREATED_AT, result.getCreatedAt());
        assertTrue(result.getAvailable());

        verify(servicePlansV3).get(argThat(request ->
                request.getServicePlanId().equals(TEST_GUID)
        ));
    }

    @Test
    @DisplayName("서비스 플랜 조회 실패 테스트")
    void get_Failure() {
        when(servicePlansV3.get(any())).thenReturn(Mono.error(new RuntimeException("Get failed")));

        assertThrows(RuntimeException.class, () -> servicePlansServiceV3.get(TEST_GUID));

        verify(servicePlansV3).get(any());
    }

    @Test
    @DisplayName("서비스 플랜 목록 조회 성공 테스트")
    void list_Success() {
        ListServicePlansResponse mockResponse = createMockListServicePlansResponse();
        when(servicePlansV3.list(any())).thenReturn(Mono.just(mockResponse));

        ListServicePlansResponse result = servicePlansServiceV3.list();

        assertNotNull(result);
        assertFalse(result.getResources().isEmpty());
        assertEquals(1, result.getResources().size());
        ServicePlanResource resource = result.getResources().get(0);
        assertEquals(TEST_ID, resource.getId());
        assertEquals(TEST_NAME, resource.getName());
        assertEquals(TEST_DESCRIPTION, resource.getDescription());
        assertTrue(resource.getFree());
        assertEquals(Visibility.ADMIN, resource.getVisibilityType());
        assertEquals(TEST_CREATED_AT, resource.getCreatedAt());
        assertTrue(resource.getAvailable());

        verify(servicePlansV3).list(any());
    }

    @Test
    @DisplayName("서비스 플랜 목록 조회 실패 테스트")
    void list_Failure() {
        when(servicePlansV3.list(any())).thenReturn(Mono.error(new RuntimeException("List failed")));

        assertThrows(RuntimeException.class, () -> servicePlansServiceV3.list());

        verify(servicePlansV3).list(any());
    }

    @Test
    @DisplayName("서비스 플랜 업데이트 성공 테스트")
    void update_Success() {
        UpdateServicePlanResponse mockResponse = createMockUpdateServicePlanResponse();
        when(servicePlansV3.update(any())).thenReturn(Mono.just(mockResponse));

        UpdateServicePlanResponse result = servicePlansServiceV3.update(TEST_GUID);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_DESCRIPTION, result.getDescription());
        assertTrue(result.getFree());
        assertEquals(Visibility.ADMIN, result.getVisibilityType());
        assertEquals(TEST_CREATED_AT, result.getCreatedAt());
        assertTrue(result.getAvailable());

        verify(servicePlansV3).update(argThat(request ->
                request.getServicePlanId().equals(TEST_GUID)
        ));
    }

    @Test
    @DisplayName("서비스 플랜 업데이트 실패 테스트")
    void update_Failure() {
        when(servicePlansV3.update(any())).thenReturn(Mono.error(new RuntimeException("Update failed")));

        assertThrows(RuntimeException.class, () -> servicePlansServiceV3.update(TEST_GUID));

        verify(servicePlansV3).update(any());
    }

    @Test
    @DisplayName("서비스 플랜 가시성 업데이트 성공 테스트")
    void updateVisibility_Success() {
        UpdateServicePlanVisibilityResponse mockResponse = UpdateServicePlanVisibilityResponse.builder()
                .type(Visibility.ADMIN)
                .build();
        when(servicePlansV3.updateVisibility(any())).thenReturn(Mono.just(mockResponse));

        UpdateServicePlanVisibilityResponse result = servicePlansServiceV3.updateVisibility(TEST_GUID);

        assertNotNull(result);
        assertEquals(Visibility.ADMIN, result.getType());

        verify(servicePlansV3).updateVisibility(argThat(request ->
                request.getServicePlanId().equals(TEST_GUID) &&
                        request.getType() == Visibility.ADMIN
        ));
    }

    @Test
    @DisplayName("서비스 플랜 가시성 업데이트 실패 테스트")
    void updateVisibility_Failure() {
        when(servicePlansV3.updateVisibility(any())).thenReturn(Mono.error(new RuntimeException("Update visibility failed")));

        assertThrows(RuntimeException.class, () -> servicePlansServiceV3.updateVisibility(TEST_GUID));

        verify(servicePlansV3).updateVisibility(any());
    }

    private GetServicePlanResponse createMockGetServicePlanResponse() {
        return GetServicePlanResponse.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .free(true)
                .visibilityType(Visibility.ADMIN)
                .createdAt(TEST_CREATED_AT)
                .available(true)
                .relationships(ServicePlanRelationships.builder()
                        .serviceOffering(ToOneRelationship.builder().build())
                        .build())
                .schemas(Schemas.builder()
                        .serviceInstance(ServiceInstanceSchema.builder()
                                .create(Schema.builder().build())
                                .build())
                        .build())
                .brokerCatalog(BrokerCatalog.builder()
                        .brokerCatalogId(TEST_BROKER_CATALOG_ID)
                        .features(Features.builder()
                                .bindable(true)
                                .planUpdateable(true)
                                .build())
                        .build())
                .build();
    }

    private ListServicePlansResponse createMockListServicePlansResponse() {
        return ListServicePlansResponse.builder()
                .resource(ServicePlanResource.builder()
                        .id(TEST_ID)
                        .name(TEST_NAME)
                        .description(TEST_DESCRIPTION)
                        .free(true)
                        .visibilityType(Visibility.ADMIN)
                        .createdAt(TEST_CREATED_AT)
                        .available(true)
                        .relationships(ServicePlanRelationships.builder()
                                .serviceOffering(ToOneRelationship.builder().build())
                                .build())
                        .schemas(Schemas.builder()
                                .serviceInstance(ServiceInstanceSchema.builder()
                                        .create(Schema.builder().build())
                                        .build())
                                .build())
                        .brokerCatalog(BrokerCatalog.builder()
                                .brokerCatalogId(TEST_BROKER_CATALOG_ID)
                                .features(Features.builder()
                                        .bindable(true)
                                        .planUpdateable(true)
                                        .build())
                                .build())
                        .build())
                .build();
    }

    private UpdateServicePlanResponse createMockUpdateServicePlanResponse() {
        return UpdateServicePlanResponse.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .free(true)
                .visibilityType(Visibility.ADMIN)
                .createdAt(TEST_CREATED_AT)
                .available(true)
                .relationships(ServicePlanRelationships.builder()
                        .serviceOffering(ToOneRelationship.builder().build())
                        .build())
                .schemas(Schemas.builder()
                        .serviceInstance(ServiceInstanceSchema.builder()
                                .create(Schema.builder().build())
                                .build())
                        .build())
                .brokerCatalog(BrokerCatalog.builder()
                        .brokerCatalogId(TEST_BROKER_CATALOG_ID)
                        .features(Features.builder()
                                .bindable(true)
                                .planUpdateable(true)
                                .build())
                        .build())
                .build();
    }
}