package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.UsageSummary;
import org.cloudfoundry.client.v3.domains.DomainRelationships;
import org.cloudfoundry.client.v3.domains.DomainResource;
import org.cloudfoundry.client.v3.organizations.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.Organization;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  Organizations ServiceV3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("OrganizationsServiceV3 테스트")
public class OrganizationsServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private OrganizationsV3 organizationsV3;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Spy
    @InjectMocks
    private OrganizationsServiceV3 organizationsServiceV3;

    @BeforeEach
    void setUp() {
        when(cloudFoundryClient.organizationsV3()).thenReturn(organizationsV3);
        doReturn(tokenProvider).when(organizationsServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(organizationsServiceV3).cloudFoundryClient(any());
    }

    @Test
    @DisplayName("기본 격리 세그먼트 할당 테스트")
    void assignDefaultIsolationSegment() {
        String organizationId = "test-org-guid";
        String isolationSegmentGuid = "test-isolation-segment-guid";
        AssignOrganizationDefaultIsolationSegmentResponse mockResponse = AssignOrganizationDefaultIsolationSegmentResponse.builder()
                .data(Relationship.builder()
                        .id(isolationSegmentGuid)
                        .build())
                .build();
        when(organizationsV3.assignDefaultIsolationSegment(any(AssignOrganizationDefaultIsolationSegmentRequest.class)))
                .thenReturn(Mono.just(mockResponse));

        AssignOrganizationDefaultIsolationSegmentResponse response = organizationsServiceV3.assignDefaultIsolationSegment(organizationId, isolationSegmentGuid);

        assertNotNull(response);
        assertEquals(isolationSegmentGuid, response.getData().getId());
        verify(organizationsV3).assignDefaultIsolationSegment(argThat(request ->
                request.getOrganizationId().equals(organizationId) &&
                        request.getData().getId().equals(isolationSegmentGuid)
        ));
    }

    @Test
    @DisplayName("조직 생성 테스트")
    void create() {
        Organization org = new Organization();
        org.setName("testOrg");
        CreateOrganizationResponse mockResponse = CreateOrganizationResponse.builder()
                .id("test-id")
                .createdAt("2023-01-01T00:00:00Z")
                .updatedAt("2023-01-01T00:00:00Z")
                .metadata(Metadata.builder().build())
                .name("testOrg")
                .build();
        when(organizationsV3.create(any(CreateOrganizationRequest.class)))
                .thenReturn(Mono.just(mockResponse));

        CreateOrganizationResponse response = organizationsServiceV3.create(org);

        assertNotNull(response);
        assertEquals("test-id", response.getId());
        assertEquals("testOrg", response.getName());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
        verify(organizationsV3).create(argThat(request ->
                request.getName().equals("testOrg")
        ));
    }

    @Test
    @DisplayName("조직 삭제 테스트")
    void delete() {
        String guid = "test-guid";
        when(organizationsV3.delete(any(DeleteOrganizationRequest.class))).thenReturn(Mono.just("Deleted"));

        String response = organizationsServiceV3.delete(guid);

        assertEquals("Deleted", response);
        verify(organizationsV3).delete(argThat(request ->
                request.getOrganizationId().equals(guid)
        ));
    }

    @Test
    @DisplayName("조직 정보 조회 테스트")
    void get() {
        String guid = "test-guid";
        GetOrganizationResponse mockResponse = GetOrganizationResponse.builder()
                .id(guid)
                .createdAt("2023-01-01T00:00:00Z")
                .updatedAt("2023-01-01T00:00:00Z")
                .metadata(Metadata.builder().build())
                .name("testOrg")
                .build();
        when(organizationsV3.get(any(GetOrganizationRequest.class))).thenReturn(Mono.just(mockResponse));

        GetOrganizationResponse response = organizationsServiceV3.get(guid);

        assertNotNull(response);
        assertEquals(guid, response.getId());
        assertEquals("testOrg", response.getName());
        verify(organizationsV3).get(argThat(request ->
                request.getOrganizationId().equals(guid)
        ));
    }

    @Test
    @DisplayName("조직의 기본 도메인 조회 테스트")
    void getDefaultDomain() {
        String guid = "test-guid";
        GetOrganizationDefaultDomainResponse mockResponse = GetOrganizationDefaultDomainResponse.builder()
                .id("domain-id")
                .createdAt("2023-01-01T00:00:00Z")
                .updatedAt("2023-01-01T00:00:00Z")
                .name("default-domain.com")
                .relationships(DomainRelationships.builder()
                        .organization(ToOneRelationship.builder().build())
                        .build())
                .isInternal(false)
                .build();
        when(organizationsV3.getDefaultDomain(any(GetOrganizationDefaultDomainRequest.class))).thenReturn(Mono.just(mockResponse));

        GetOrganizationDefaultDomainResponse response = organizationsServiceV3.getDefaultDomain(guid);

        assertNotNull(response);
        assertEquals("domain-id", response.getId());
        assertEquals("default-domain.com", response.getName());
        assertFalse(response.isInternal());
        verify(organizationsV3).getDefaultDomain(argThat(request ->
                request.getOrganizationId().equals(guid)
        ));
    }

    @Test
    @DisplayName("조직의 기본 격리 세그먼트 조회 테스트")
    void getDefaultIsolationSegment() {
        String guid = "test-guid";
        GetOrganizationDefaultIsolationSegmentResponse mockResponse = GetOrganizationDefaultIsolationSegmentResponse.builder()
                .data(Relationship.builder()
                        .id("isolation-segment-id")
                        .build())
                .build();
        when(organizationsV3.getDefaultIsolationSegment(any(GetOrganizationDefaultIsolationSegmentRequest.class)))
                .thenReturn(Mono.just(mockResponse));

        GetOrganizationDefaultIsolationSegmentResponse response = organizationsServiceV3.getDeafultIsolationSegment(guid);

        assertNotNull(response);
        assertEquals("isolation-segment-id", response.getData().getId());
        verify(organizationsV3).getDefaultIsolationSegment(argThat(request ->
                request.getOrganizationId().equals(guid)
        ));
    }

    @Test
    @DisplayName("조직 사용량 요약 조회 테스트")
    void getUsageSummary() {
        String guid = "test-guid";
        GetOrganizationUsageSummaryResponse mockResponse = GetOrganizationUsageSummaryResponse.builder()
                .usageSummary(UsageSummary.builder()
                        .memoryInMb(1024)
                        .startedInstances(5)
                        .build())
                .build();
        when(organizationsV3.getUsageSummary(any(GetOrganizationUsageSummaryRequest.class))).thenReturn(Mono.just(mockResponse));

        GetOrganizationUsageSummaryResponse response = organizationsServiceV3.getUsageSummary(guid);

        assertNotNull(response);
        assertEquals(1024, response.getUsageSummary().getMemoryInMb());
        assertEquals(5, response.getUsageSummary().getStartedInstances());
        verify(organizationsV3).getUsageSummary(argThat(request ->
                request.getOrganizationId().equals(guid)
        ));
    }

    @Test
    @DisplayName("조직 목록 조회 테스트")
    void list() {
        ListOrganizationsResponse mockResponse = ListOrganizationsResponse.builder()
                .addAllResources(Arrays.asList(
                        OrganizationResource.builder().id("org1").name("Org1")
                                .createdAt("2023-01-01T00:00:00Z")
                                .metadata(Metadata.builder().build())
                                .build(),
                        OrganizationResource.builder().id("org2").name("Org2")
                                .createdAt("2023-01-01T00:00:00Z")
                                .metadata(Metadata.builder().build())
                                .build()
                ))
                .build();
        when(organizationsV3.list(any(ListOrganizationsRequest.class))).thenReturn(Mono.just(mockResponse));

        ListOrganizationsResponse response = organizationsServiceV3.list(Arrays.asList("Org1", "Org2"));

        assertNotNull(response);
        assertEquals(2, response.getResources().size());
        assertEquals("org1", response.getResources().get(0).getId());
        assertEquals("Org1", response.getResources().get(0).getName());
        verify(organizationsV3).list(argThat(request ->
                request.getNames().containsAll(Arrays.asList("Org1", "Org2"))
        ));
    }

    @Test
    @DisplayName("조직의 도메인 목록 조회 테스트")
    void listDomains() {
        String orgGuid = "org-guid";
        ListOrganizationDomainsResponse mockResponse = ListOrganizationDomainsResponse.builder()
                .addAllResources(Arrays.asList(
                        DomainResource.builder().id("domain1").name("domain1.com")
                                .createdAt("2023-01-01T00:00:00Z")
                                .metadata(Metadata.builder().build())
                                .relationships(DomainRelationships.builder()
                                        .organization(ToOneRelationship.builder().build())
                                        .build())
                                .isInternal(false)
                                .build(),
                        DomainResource.builder().id("domain2").name("domain2.com")
                                .createdAt("2023-01-01T00:00:00Z")
                                .metadata(Metadata.builder().build())
                                .relationships(DomainRelationships.builder()
                                        .organization(ToOneRelationship.builder().build())
                                        .build())
                                .isInternal(false)
                                .build()
                ))
                .build();
        when(organizationsV3.listDomains(any(ListOrganizationDomainsRequest.class))).thenReturn(Mono.just(mockResponse));

        ListOrganizationDomainsResponse response = organizationsServiceV3
                .listDomains(orgGuid, Arrays.asList("domain1", "domain2"), Arrays.asList("domain1.com", "domain2.com"));

        assertNotNull(response);
        assertEquals(2, response.getResources().size());
        assertEquals("domain1", response.getResources().get(0).getId());
        assertEquals("domain1.com", response.getResources().get(0).getName());
        verify(organizationsV3).listDomains(argThat(request ->
                request.getOrganizationId().equals(orgGuid) &&
                        request.getDomainIds().containsAll(Arrays.asList("domain1", "domain2")) &&
                        request.getNames().containsAll(Arrays.asList("domain1.com", "domain2.com"))
        ));
    }

    @Test
    @DisplayName("조직 정보 업데이트 테스트")
    void update() {
        String guid = "test-guid";
        UpdateOrganizationResponse mockResponse = UpdateOrganizationResponse.builder()
                .id(guid)
                .createdAt("2023-01-01T00:00:00Z")
                .updatedAt("2023-01-01T00:00:00Z")
                .metadata(Metadata.builder().build())
                .name("updatedOrg")
                .build();
        when(organizationsV3.update(any(UpdateOrganizationRequest.class))).thenReturn(Mono.just(mockResponse));

        UpdateOrganizationResponse response = organizationsServiceV3.update(guid);

        assertNotNull(response);
        assertEquals(guid, response.getId());
        assertEquals("updatedOrg", response.getName());
        verify(organizationsV3).update(argThat(request ->
                request.getOrganizationId().equals(guid)
        ));
    }
}