package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.isolationsegments.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.IsolationSegment;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  IsolationSegments Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
class IsolationSegmentsServiceTest {

    @Mock
    private IsolationSegments isolationSegments;

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @InjectMocks
    private IsolationSegmentsService isolationSegmentsService;

    @BeforeEach
    void setUp() {
        isolationSegmentsService = spy(new IsolationSegmentsService());
        doReturn(tokenProvider).when(isolationSegmentsService).tokenProvider();
        doReturn(cloudFoundryClient).when(isolationSegmentsService).cloudFoundryClient(any());
        when(cloudFoundryClient.isolationSegments()).thenReturn(isolationSegments);
    }

    @Test
    @DisplayName("조직 자격 부여 추가 기능 테스트")
    void addOrganizationEntitlement() {
        AddIsolationSegmentOrganizationEntitlementResponse mockResponse = mock(AddIsolationSegmentOrganizationEntitlementResponse.class);
        when(isolationSegments.addOrganizationEntitlement(any())).thenReturn(Mono.just(mockResponse));

        AddIsolationSegmentOrganizationEntitlementResponse result = isolationSegmentsService.addOrganizationEntitlement("guid");

        assertNotNull(result);
        verify(isolationSegments).addOrganizationEntitlement(any());
    }

    @Test
    @DisplayName("격리 세그먼트 생성 기능 테스트")
    void create() {
        CreateIsolationSegmentResponse mockResponse = mock(CreateIsolationSegmentResponse.class);
        when(isolationSegments.create(any())).thenReturn(Mono.just(mockResponse));

        IsolationSegment isolationSegment = new IsolationSegment();
        isolationSegment.setName("testName");
        CreateIsolationSegmentResponse result = isolationSegmentsService.create(isolationSegment);

        assertNotNull(result);
        verify(isolationSegments).create(any());
    }

    @Test
    @DisplayName("격리 세그먼트 삭제 기능 테스트")
    void delete() {
        when(isolationSegments.delete(any())).thenReturn(Mono.empty());

        Void result = isolationSegmentsService.delete("guid");

        assertNull(result);
        verify(isolationSegments).delete(any());
    }

    @Test
    @DisplayName("격리 세그먼트 조회 기능 테스트")
    void get() {
        GetIsolationSegmentResponse mockResponse = mock(GetIsolationSegmentResponse.class);
        when(isolationSegments.get(any())).thenReturn(Mono.just(mockResponse));

        GetIsolationSegmentResponse result = isolationSegmentsService.get("guid");

        assertNotNull(result);
        verify(isolationSegments).get(any());
    }

    @Test
    @DisplayName("격리 세그먼트 목록 조회 기능 테스트")
    void list() {
        ListIsolationSegmentsResponse mockResponse = mock(ListIsolationSegmentsResponse.class);
        when(isolationSegments.list(any())).thenReturn(Mono.just(mockResponse));

        ListIsolationSegmentsResponse result = isolationSegmentsService.list();

        assertNotNull(result);
        verify(isolationSegments).list(any());
    }

    @Test
    @DisplayName("자격이 부여된 조직 목록 조회 기능 테스트")
    void listEntitledOrganizations() {
        ListIsolationSegmentEntitledOrganizationsResponse mockResponse = mock(ListIsolationSegmentEntitledOrganizationsResponse.class);
        when(isolationSegments.listEntitledOrganizations(any())).thenReturn(Mono.just(mockResponse));

        ListIsolationSegmentEntitledOrganizationsResponse result = isolationSegmentsService.listEntitledOrganizations("guid");

        assertNotNull(result);
        verify(isolationSegments).listEntitledOrganizations(any());
    }

    @Test
    @DisplayName("조직 관계 목록 조회 기능 테스트")
    void listOrganizationsRelationship() {
        ListIsolationSegmentOrganizationsRelationshipResponse mockResponse = mock(ListIsolationSegmentOrganizationsRelationshipResponse.class);
        when(isolationSegments.listOrganizationsRelationship(any())).thenReturn(Mono.just(mockResponse));

        ListIsolationSegmentOrganizationsRelationshipResponse result = isolationSegmentsService.listOrganizationsRelationship("guid");

        assertNotNull(result);
        verify(isolationSegments).listOrganizationsRelationship(any());
    }

    @Test
    @DisplayName("공간 관계 목록 조회 기능 테스트")
    void listSpacesRelationship() {
        ListIsolationSegmentSpacesRelationshipResponse mockResponse = mock(ListIsolationSegmentSpacesRelationshipResponse.class);
        when(isolationSegments.listSpacesRelationship(any())).thenReturn(Mono.just(mockResponse));

        ListIsolationSegmentSpacesRelationshipResponse result = isolationSegmentsService.listSpacesRelationship("guid");

        assertNotNull(result);
        verify(isolationSegments).listSpacesRelationship(any());
    }

    @Test
    @DisplayName("조직 자격 제거 기능 테스트")
    void removeOrganizationEntitlement() {
        when(isolationSegments.removeOrganizationEntitlement(any())).thenReturn(Mono.empty());

        Void result = isolationSegmentsService.removeOrganizationEntitlement("guid");

        assertNull(result);
        verify(isolationSegments).removeOrganizationEntitlement(any());
    }

    @Test
    @DisplayName("격리 세그먼트 업데이트 기능 테스트")
    void update() {
        UpdateIsolationSegmentResponse mockResponse = mock(UpdateIsolationSegmentResponse.class);
        when(isolationSegments.update(any())).thenReturn(Mono.just(mockResponse));

        UpdateIsolationSegmentResponse result = isolationSegmentsService.update("guid");

        assertNotNull(result);
        verify(isolationSegments).update(any());
    }
}