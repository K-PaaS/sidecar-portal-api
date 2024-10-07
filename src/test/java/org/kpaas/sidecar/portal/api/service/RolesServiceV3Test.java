package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.roles.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.Role;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  Roles Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("RolesServiceV3 테스트")
class RolesServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Mock
    private RolesV3 rolesV3;
    @InjectMocks
    private RolesServiceV3 rolesServiceV3;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ORG_ID = "test-org-id";
    private static final String TEST_USER_ID = "test-user-id";

    @BeforeEach
    void setUp() {
        rolesServiceV3 = spy(new RolesServiceV3());
        doReturn(tokenProvider).when(rolesServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(rolesServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.rolesV3()).thenReturn(rolesV3);
    }

    @Test
    @DisplayName("새로운 역할 생성 테스트")
    void create() {
        Role role = new Role();
        role.setType(RoleType.ORGANIZATION_USER);
        role.setRelationships(RoleRelationships.builder()
                .organization(ToOneRelationship.builder()
                        .data(Relationship.builder()
                                .id(TEST_ORG_ID)
                                .build())
                        .build())
                .user(ToOneRelationship.builder()
                        .data(Relationship.builder()
                                .id(TEST_USER_ID)
                                .build())
                        .build())
                .build());

        CreateRoleResponse mockResponse = CreateRoleResponse.builder()
                .id("test-role-id")
                .type(RoleType.ORGANIZATION_USER)
                .createdAt(TEST_CREATED_AT)
                .relationships(role.getRelationships())
                .build();

        when(rolesV3.create(any(CreateRoleRequest.class))).thenReturn(Mono.just(mockResponse));

        CreateRoleResponse result = rolesServiceV3.create(role);

        assertNotNull(result);
        assertEquals("test-role-id", result.getId());
        assertEquals(RoleType.ORGANIZATION_USER, result.getType());
        verify(rolesV3).create(any(CreateRoleRequest.class));
    }

    @Test
    @DisplayName("역할 삭제 테스트")
    void delete() {
        String guid = "test-guid";
        when(rolesV3.delete(any(DeleteRoleRequest.class))).thenReturn(Mono.empty());

        String result = rolesServiceV3.delete(guid);

        assertNull(result);
        verify(rolesV3).delete(any(DeleteRoleRequest.class));
    }

    @Test
    @DisplayName("특정 역할 조회 테스트")
    void get() {
        String guid = "test-guid";
        GetRoleResponse mockResponse = GetRoleResponse.builder()
                .id(guid)
                .type(RoleType.ORGANIZATION_USER)
                .createdAt(TEST_CREATED_AT)
                .relationships(RoleRelationships.builder()
                        .organization(ToOneRelationship.builder()
                                .data(Relationship.builder()
                                        .id(TEST_ORG_ID)
                                        .build())
                                .build())
                        .user(ToOneRelationship.builder()
                                .data(Relationship.builder()
                                        .id(TEST_USER_ID)
                                        .build())
                                .build())
                        .build())
                .build();

        when(rolesV3.get(any(GetRoleRequest.class))).thenReturn(Mono.just(mockResponse));

        GetRoleResponse result = rolesServiceV3.get(guid);

        assertNotNull(result);
        assertEquals(guid, result.getId());
        assertEquals(RoleType.ORGANIZATION_USER, result.getType());
        verify(rolesV3).get(any(GetRoleRequest.class));
    }

    @Test
    @DisplayName("역할 목록 조회 테스트")
    void list() {
        List<String> orgGuids = Arrays.asList("org1", "org2");
        List<String> spaceGuids = Arrays.asList("space1", "space2");
        List<String> usernames = Arrays.asList("user1", "user2");
        List<RoleType> types = Arrays.asList(RoleType.ORGANIZATION_USER, RoleType.SPACE_DEVELOPER);
        List<String> roleGuids = Arrays.asList("role1", "role2");

        ListRolesResponse mockResponse = ListRolesResponse.builder()
                .resources(Collections.singletonList(
                        RoleResource.builder()
                                .id("test-role-id")
                                .type(RoleType.ORGANIZATION_USER)
                                .createdAt(TEST_CREATED_AT)
                                .relationships(RoleRelationships.builder()
                                        .organization(ToOneRelationship.builder()
                                                .data(Relationship.builder()
                                                        .id(TEST_ORG_ID)
                                                        .build())
                                                .build())
                                        .user(ToOneRelationship.builder()
                                                .data(Relationship.builder()
                                                        .id(TEST_USER_ID)
                                                        .build())
                                                .build())
                                        .build())
                                .build()
                ))
                .build();

        when(rolesV3.list(any(ListRolesRequest.class))).thenReturn(Mono.just(mockResponse));

        ListRolesResponse result = rolesServiceV3.list(orgGuids, spaceGuids, usernames, types, roleGuids);

        assertNotNull(result);
        assertEquals(1, result.getResources().size());
        assertEquals("test-role-id", result.getResources().get(0).getId());
        assertEquals(RoleType.ORGANIZATION_USER, result.getResources().get(0).getType());
        verify(rolesV3).list(any(ListRolesRequest.class));
    }

    @Test
    @DisplayName("null 입력값으로 역할 목록 조회 테스트")
    void listWithNullInputs() {
        ListRolesResponse mockResponse = ListRolesResponse.builder()
                .resources(Collections.emptyList())
                .build();

        when(rolesV3.list(any(ListRolesRequest.class))).thenReturn(Mono.just(mockResponse));

        ListRolesResponse result = rolesServiceV3.list(null, null, null, null, null);

        assertNotNull(result);
        assertTrue(result.getResources().isEmpty());
        verify(rolesV3).list(any(ListRolesRequest.class));
    }
}