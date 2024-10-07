package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.routes.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.Route;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  Routes Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("Routes Service V3 테스트")
class RoutesServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Mock
    private RoutesV3 routesV3;
    @InjectMocks
    private RoutesServiceV3 routesServiceV3;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ROUTE_ID = "route-id";
    private static final String TEST_ROUTE_HOST = "testHost";
    private static final String TEST_ROUTE_PATH = "/testPath";
    private static final String TEST_ROUTE_URL = "testHost.example.com/testPath";

    @BeforeEach
    void setUp() {
        routesServiceV3 = spy(new RoutesServiceV3());
        doReturn(tokenProvider).when(routesServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(routesServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.routesV3()).thenReturn(routesV3);
    }

    @Test
    @DisplayName("새로운 라우트 생성 테스트")
    void create() {
        Route route = new Route();
        route.setHost(TEST_ROUTE_HOST);
        route.setPath(TEST_ROUTE_PATH);
        route.setUrl(TEST_ROUTE_URL);
        RouteRelationships relationships = RouteRelationships.builder()
                .domain(ToOneRelationship.builder()
                        .data(Relationship.builder().id("domain-id").build())
                        .build())
                .space(ToOneRelationship.builder()
                        .data(Relationship.builder().id("space-id").build())
                        .build())
                .build();
        route.setRelationships(relationships);

        CreateRouteResponse mockResponse = CreateRouteResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ROUTE_ID)
                .host(route.getHost())
                .path(route.getPath())
                .url(route.getUrl())
                .relationships(relationships)
                .build();

        when(routesV3.create(any())).thenReturn(Mono.just(mockResponse));

        CreateRouteResponse result = routesServiceV3.create(route);

        assertNotNull(result);
        assertEquals(TEST_ROUTE_ID, result.getId());
        assertEquals(TEST_ROUTE_HOST, result.getHost());
        assertEquals(TEST_ROUTE_PATH, result.getPath());
        assertEquals(TEST_ROUTE_URL, result.getUrl());
        verify(routesV3).create(any());
    }

    @Test
    @DisplayName("라우트 삭제 테스트")
    void delete() {
        String guid = "testGuid";
        when(routesV3.delete(any())).thenReturn(Mono.just("Deleted"));

        String result = routesServiceV3.delete(guid);

        assertEquals("Deleted", result);
        verify(routesV3).delete(any());
    }

    @Test
    @DisplayName("라우트 정보 조회 테스트")
    void get() {
        String guid = "testGuid";
        GetRouteResponse mockResponse = GetRouteResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ROUTE_ID)
                .host(TEST_ROUTE_HOST)
                .path(TEST_ROUTE_PATH)
                .url(TEST_ROUTE_URL)
                .relationships(RouteRelationships.builder()
                        .domain(ToOneRelationship.builder().build())
                        .space(ToOneRelationship.builder().build())
                        .build())
                .build();

        when(routesV3.get(any())).thenReturn(Mono.just(mockResponse));

        GetRouteResponse result = routesServiceV3.get(guid);

        assertNotNull(result);
        assertEquals(TEST_ROUTE_ID, result.getId());
        assertEquals(TEST_ROUTE_HOST, result.getHost());
        assertEquals(TEST_ROUTE_PATH, result.getPath());
        assertEquals(TEST_ROUTE_URL, result.getUrl());
        verify(routesV3).get(any());
    }

    @Test
    @DisplayName("라우트 목적지 추가 테스트")
    void insertDestinations() {
        String routeGuid = "routeGuid";
        String appGuid = "appGuid";
        InsertRouteDestinationsResponse mockResponse = InsertRouteDestinationsResponse.builder().build();

        when(routesV3.insertDestinations(any())).thenReturn(Mono.just(mockResponse));

        InsertRouteDestinationsResponse result = routesServiceV3.insertDestinations(routeGuid, appGuid);

        assertNotNull(result);
        verify(routesV3).insertDestinations(any());
    }

    @Test
    @DisplayName("라우트 목록 조회 테스트")
    void list() {
        ListRoutesResponse mockResponse = ListRoutesResponse.builder().build();
        when(routesV3.list(any())).thenReturn(Mono.just(mockResponse));

        ListRoutesResponse result = routesServiceV3.list(
                Collections.singletonList("appGuid"),
                Collections.singletonList("domainGuid"),
                Collections.singletonList("host"),
                Collections.singletonList("orgGuid"),
                Collections.singletonList("path"),
                Collections.singletonList(8080),
                Collections.singletonList("spaceGuid")
        );

        assertNotNull(result);
        verify(routesV3).list(any());
    }

    @Test
    @DisplayName("라우트 목적지 목록 조회 테스트")
    void listDestinations() {
        String routeGuid = "routeGuid";
        ListRouteDestinationsResponse mockResponse = ListRouteDestinationsResponse.builder().build();

        when(routesV3.listDestinations(any())).thenReturn(Mono.just(mockResponse));

        ListRouteDestinationsResponse result = routesServiceV3.listDestinations(routeGuid, Arrays.asList("appGuid1", "appGuid2"));

        assertNotNull(result);
        verify(routesV3).listDestinations(any());
    }

    @Test
    @DisplayName("라우트 목적지 제거 테스트")
    void removeDestinations() {
        String routeGuid = "routeGuid";
        String appGuid = "appGuid";
        String destinationId = "destinationId";

        ListRouteDestinationsResponse mockListResponse = ListRouteDestinationsResponse.builder()
                .destinations(Collections.singletonList(Destination.builder()
                        .destinationId(destinationId)
                        .application(Application.builder()
                                .applicationId(appGuid)
                                .build())
                        .build()))
                .build();

        when(routesV3.listDestinations(any())).thenReturn(Mono.just(mockListResponse));
        when(routesV3.removeDestinations(any())).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> routesServiceV3.removeDestinations(routeGuid, appGuid));
        verify(routesV3).removeDestinations(any());
    }

    @Test
    @DisplayName("라우트 목적지 교체 테스트")
    void replaceDestinations() {
        String guid = "testGuid";
        ReplaceRouteDestinationsResponse mockResponse = ReplaceRouteDestinationsResponse.builder().build();

        when(routesV3.replaceDestinations(any())).thenReturn(Mono.just(mockResponse));

        ReplaceRouteDestinationsResponse result = routesServiceV3.replaceDestinations(guid);

        assertNotNull(result);
        verify(routesV3).replaceDestinations(any());
    }

    @Test
    @DisplayName("라우트 정보 업데이트 테스트")
    void update() {
        String guid = "testGuid";
        UpdateRouteResponse mockResponse = UpdateRouteResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ROUTE_ID)
                .host(TEST_ROUTE_HOST)
                .path(TEST_ROUTE_PATH)
                .url(TEST_ROUTE_URL)
                .relationships(RouteRelationships.builder()
                        .domain(ToOneRelationship.builder().build())
                        .space(ToOneRelationship.builder().build())
                        .build())
                .build();

        when(routesV3.update(any())).thenReturn(Mono.just(mockResponse));

        UpdateRouteResponse result = routesServiceV3.update(guid);

        assertNotNull(result);
        assertEquals(TEST_ROUTE_ID, result.getId());
        assertEquals(TEST_ROUTE_HOST, result.getHost());
        assertEquals(TEST_ROUTE_PATH, result.getPath());
        assertEquals(TEST_ROUTE_URL, result.getUrl());
        verify(routesV3).update(any());
    }
}