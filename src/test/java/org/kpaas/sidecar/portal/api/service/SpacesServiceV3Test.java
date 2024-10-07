package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.spaces.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.Space;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  Spaces Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("SpacesServiceV3 테스트")
class SpacesServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private SpacesV3 spacesV3;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Spy
    @InjectMocks
    private SpacesServiceV3 spacesServiceV3;

    @BeforeEach
    void setUp() {
        when(cloudFoundryClient.spacesV3()).thenReturn(spacesV3);
        doReturn(tokenProvider).when(spacesServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(spacesServiceV3).cloudFoundryClient(any());
    }

    @Test
    @DisplayName("매니페스트 적용: 유효한 GUID로 매니페스트를 성공적으로 적용해야 함")
    void applyManifest() {
        String guid = "test-guid";
        ApplyManifestResponse mockResponse = ApplyManifestResponse.builder().build();
        when(spacesV3.applyManifest(any(ApplyManifestRequest.class))).thenReturn(Mono.just(mockResponse));

        ApplyManifestResponse response = spacesServiceV3.applyManifest(guid);

        assertNotNull(response);
        verify(spacesV3).applyManifest(argThat(request ->
                request.getSpaceId().equals(guid)
        ));
    }

    @Test
    @DisplayName("격리 세그먼트 할당: 유효한 GUID로 격리 세그먼트를 성공적으로 할당해야 함")
    void assignIsolationSegment() {
        String guid = "test-guid";
        AssignSpaceIsolationSegmentResponse mockResponse = AssignSpaceIsolationSegmentResponse.builder().build();
        when(spacesV3.assignIsolationSegment(any(AssignSpaceIsolationSegmentRequest.class))).thenReturn(Mono.just(mockResponse));

        AssignSpaceIsolationSegmentResponse response = spacesServiceV3.assignIsolationSegment(guid);

        assertNotNull(response);
        verify(spacesV3).assignIsolationSegment(any(AssignSpaceIsolationSegmentRequest.class));
    }

    @Test
    @DisplayName("공간 생성: 유효한 공간 정보로 새 공간을 성공적으로 생성해야 함")
    void create() {
        String name = "test-space";
        String orgId = "org-id";
        Space space = new Space();
        space.setName(name);
        space.setRelationships(SpaceRelationships.builder()
                .organization(ToOneRelationship.builder()
                        .data(Relationship.builder()
                                .id(orgId)
                                .build())
                        .build())
                .build());
        CreateSpaceResponse mockResponse = CreateSpaceResponse.builder()
                .id("space-id")
                .name(name)
                .createdAt("2023-01-01T00:00:00Z")
                .build();
        when(spacesV3.create(any(CreateSpaceRequest.class))).thenReturn(Mono.just(mockResponse));

        CreateSpaceResponse response = spacesServiceV3.create(space);

        assertNotNull(response);
        assertEquals("space-id", response.getId());
        assertEquals(name, response.getName());
        verify(spacesV3).create(argThat(request ->
                request.getName().equals(name) &&
                        request.getRelationships().getOrganization().getData().getId().equals(orgId)
        ));
    }

    @Test
    @DisplayName("공간 삭제: 유효한 GUID로 공간을 성공적으로 삭제해야 함")
    void delete() {
        String guid = "test-guid";
        when(spacesV3.delete(any(DeleteSpaceRequest.class))).thenReturn(Mono.just("Deleted"));

        String response = spacesServiceV3.delete(guid);

        assertEquals("Deleted", response);
        verify(spacesV3).delete(argThat(request ->
                request.getSpaceId().equals(guid)
        ));
    }

    @Test
    @DisplayName("매핑되지 않은 라우트 삭제: 유효한 GUID로 매핑되지 않은 라우트를 성공적으로 삭제해야 함")
    void deleteUnmappedRoutes() {
        String guid = "test-guid";
        when(spacesV3.deleteUnmappedRoutes(any(DeleteUnmappedRoutesRequest.class))).thenReturn(Mono.just("Deleted"));

        String response = spacesServiceV3.deleteUnmappedRoutes(guid);

        assertEquals("Deleted", response);
        verify(spacesV3).deleteUnmappedRoutes(any(DeleteUnmappedRoutesRequest.class));
    }

    @Test
    @DisplayName("공간 조회: 유효한 GUID로 공간 정보를 성공적으로 조회해야 함")
    void get() {
        String guid = "test-guid";
        GetSpaceResponse mockResponse = GetSpaceResponse.builder()
                .id(guid)
                .createdAt("2023-01-01T00:00:00Z")
                .name("test-space")
                .build();
        when(spacesV3.get(any(GetSpaceRequest.class))).thenReturn(Mono.just(mockResponse));

        GetSpaceResponse response = spacesServiceV3.get(guid);

        assertNotNull(response);
        assertEquals(guid, response.getId());
        assertEquals("test-space", response.getName());
        verify(spacesV3).get(argThat(request ->
                request.getSpaceId().equals(guid)
        ));
    }

    @Test
    @DisplayName("격리 세그먼트 조회: 유효한 GUID로 격리 세그먼트 정보를 성공적으로 조회해야 함")
    void getIsolationSegment() {
        String guid = "test-guid";
        GetSpaceIsolationSegmentResponse mockResponse = GetSpaceIsolationSegmentResponse.builder().build();
        when(spacesV3.getIsolationSegment(any(GetSpaceIsolationSegmentRequest.class))).thenReturn(Mono.just(mockResponse));

        GetSpaceIsolationSegmentResponse response = spacesServiceV3.getIsolationSegment(guid);

        assertNotNull(response);
        verify(spacesV3).getIsolationSegment(any(GetSpaceIsolationSegmentRequest.class));
    }

    @Test
    @DisplayName("공간 목록 조회: 유효한 조직 GUID와 이름으로 공간 목록을 성공적으로 조회해야 함")
    void list() {
        List<String> orgGuids = Arrays.asList("org1", "org2");
        List<String> names = Arrays.asList("space1", "space2");
        ListSpacesResponse mockResponse = ListSpacesResponse.builder()
                .addAllResources(Arrays.asList(
                        SpaceResource.builder()
                                .createdAt("2023-01-01T00:00:00Z")
                                .id("space1").name("space1").build(),
                        SpaceResource.builder()
                                .createdAt("2023-01-01T00:00:00Z")
                                .id("space2").name("space2").build()
                ))
                .build();
        when(spacesV3.list(any(ListSpacesRequest.class))).thenReturn(Mono.just(mockResponse));

        ListSpacesResponse response = spacesServiceV3.list(orgGuids, names);

        assertNotNull(response);
        assertEquals(2, response.getResources().size());
        assertEquals("space1", response.getResources().get(0).getName());
        assertEquals("space2", response.getResources().get(1).getName());
        verify(spacesV3).list(argThat(request ->
                request.getOrganizationIds().containsAll(orgGuids) &&
                        request.getNames().containsAll(names)
        ));
    }

    @Test
    @DisplayName("공간 업데이트: 유효한 GUID로 공간 정보를 성공적으로 업데이트해야 함")
    void update() {
        String guid = "test-guid";
        UpdateSpaceResponse mockResponse = UpdateSpaceResponse.builder()
                .id(guid)
                .name("updated-space")
                .createdAt("2023-01-01T00:00:00Z")
                .build();
        when(spacesV3.update(any(UpdateSpaceRequest.class))).thenReturn(Mono.just(mockResponse));

        UpdateSpaceResponse response = spacesServiceV3.update(guid);

        assertNotNull(response);
        assertEquals(guid, response.getId());
        assertEquals("updated-space", response.getName());
        verify(spacesV3).update(any(UpdateSpaceRequest.class));
    }
}