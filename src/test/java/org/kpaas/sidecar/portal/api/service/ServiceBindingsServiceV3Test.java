package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.servicebindings.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.ServiceBinding;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  ServiceBinding Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceBindingsServiceV3 테스트")
class ServiceBindingsServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Mock
    private ServiceBindingsV3 serviceBindingsV3;
    @InjectMocks
    private ServiceBindingsServiceV3 serviceBindingsServiceV3;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ID = "service-binding-id";

    @BeforeEach
    void setUp() {
        serviceBindingsServiceV3 = spy(new ServiceBindingsServiceV3());
        doReturn(tokenProvider).when(serviceBindingsServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(serviceBindingsServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.serviceBindingsV3()).thenReturn(serviceBindingsV3);
    }

    @Test
    @DisplayName("서비스 바인딩 생성 테스트")
    void create() {
        ServiceBinding serviceBinding = new ServiceBinding();
        serviceBinding.setType(ServiceBindingType.APPLICATION);
        serviceBinding.setRelationships(ServiceBindingRelationships.builder()
                .serviceInstance(ToOneRelationship.builder().build())
                .build());
        CreateServiceBindingResponse expectedResponse = CreateServiceBindingResponse.builder().build();
        when(serviceBindingsV3.create(any())).thenReturn(Mono.just(expectedResponse));

        CreateServiceBindingResponse actualResponse = serviceBindingsServiceV3.create(serviceBinding);

        assertEquals(expectedResponse, actualResponse);
        verify(serviceBindingsV3).create(any());
    }

    @Test
    @DisplayName("서비스 바인딩 삭제 테스트")
    void delete() {
        String guid = "test-guid";
        when(serviceBindingsV3.delete(any())).thenReturn(Mono.just("Deleted"));

        String result = serviceBindingsServiceV3.delete(guid);

        assertEquals("Deleted", result);
        verify(serviceBindingsV3).delete(any());
    }

    @Test
    @DisplayName("서비스 바인딩 조회 테스트")
    void get() {
        String guid = "test-guid";
        GetServiceBindingResponse expectedResponse = GetServiceBindingResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .type(ServiceBindingType.APPLICATION)
                .relationships(ServiceBindingRelationships.builder()
                        .serviceInstance(ToOneRelationship.builder().build())
                        .build())
                .build();
        when(serviceBindingsV3.get(any())).thenReturn(Mono.just(expectedResponse));

        GetServiceBindingResponse actualResponse = serviceBindingsServiceV3.get(guid);

        assertEquals(expectedResponse, actualResponse);
        verify(serviceBindingsV3).get(any());
    }

    @Test
    @DisplayName("서비스 바인딩 상세 정보 조회 테스트")
    void getDetails() {
        String guid = "test-guid";
        GetServiceBindingDetailsResponse expectedResponse = GetServiceBindingDetailsResponse.builder().build();
        when(serviceBindingsV3.getDetails(any())).thenReturn(Mono.just(expectedResponse));

        GetServiceBindingDetailsResponse actualResponse = serviceBindingsServiceV3.getDetails(guid);

        assertEquals(expectedResponse, actualResponse);
        verify(serviceBindingsV3).getDetails(any());
    }

    @Test
    @DisplayName("서비스 바인딩 파라미터 조회 테스트")
    void getParameters() {
        String guid = "test-guid";
        GetServiceBindingParametersResponse expectedResponse = GetServiceBindingParametersResponse.builder().build();
        when(serviceBindingsV3.getParameters(any())).thenReturn(Mono.just(expectedResponse));

        GetServiceBindingParametersResponse actualResponse = serviceBindingsServiceV3.getParameters(guid);

        assertEquals(expectedResponse, actualResponse);
        verify(serviceBindingsV3).getParameters(any());
    }

    @Test
    @DisplayName("서비스 바인딩 목록 조회 테스트")
    void list() {
        List<String> appGuids = Arrays.asList("app-guid-1", "app-guid-2");
        List<String> appNames = Arrays.asList("app-name-1", "app-name-2");
        List<String> serviceInstanceGuids = Arrays.asList("instance-guid-1", "instance-guid-2");
        List<String> serviceInstanceNames = Arrays.asList("instance-name-1", "instance-name-2");
        List<String> servicePlanGuids = Arrays.asList("plan-guid-1", "plan-guid-2");
        List<String> servicePlanNames = Arrays.asList("plan-name-1", "plan-name-2");
        ListServiceBindingsResponse expectedResponse = ListServiceBindingsResponse.builder().build();
        when(serviceBindingsV3.list(any())).thenReturn(Mono.just(expectedResponse));

        ListServiceBindingsResponse actualResponse = serviceBindingsServiceV3.list(
                appGuids, appNames, serviceInstanceGuids, serviceInstanceNames, servicePlanGuids, servicePlanNames);

        assertEquals(expectedResponse, actualResponse);
        verify(serviceBindingsV3).list(any());
    }

    @Test
    @DisplayName("서비스 바인딩 업데이트 테스트")
    void update() {
        String guid = "test-guid";
        UpdateServiceBindingResponse expectedResponse = UpdateServiceBindingResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .type(ServiceBindingType.APPLICATION)
                .relationships(ServiceBindingRelationships.builder()
                        .serviceInstance(ToOneRelationship.builder().build())
                        .build())
                .build();
        when(serviceBindingsV3.update(any())).thenReturn(Mono.just(expectedResponse));

        UpdateServiceBindingResponse actualResponse = serviceBindingsServiceV3.update(guid);

        assertEquals(expectedResponse, actualResponse);
        verify(serviceBindingsV3).update(any());
    }
}