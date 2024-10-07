package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.domains.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.model.Domain;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *  Domains Service V3 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("DomainsServiceV3 테스트")
class DomainsServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;
    @Mock
    private TokenGrantTokenProvider tokenProvider;
    @Mock
    private DomainsV3 domainsV3;
    @InjectMocks
    private DomainsServiceV3 domainsServiceV3;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ID = "domains-service-id";
    private static final String TEST_NAME = "domains-service-name";

    @BeforeEach
    void setUp() {
        domainsServiceV3 = spy(new DomainsServiceV3());
        doReturn(tokenProvider).when(domainsServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(domainsServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.domainsV3()).thenReturn(domainsV3);
    }

    @Test
    @DisplayName("예약된 라우트 확인")
    void checkReservedRoutes() {
        String guid = "testGuid";
        CheckReservedRoutesResponse mockResponse = CheckReservedRoutesResponse.builder()
                .matchingRoute(true)
                .build();
        when(domainsV3.checkReservedRoutes(any())).thenReturn(Mono.just(mockResponse));

        CheckReservedRoutesResponse response = domainsServiceV3.checkReservedRoutes(guid);

        assertNotNull(response);
        assertTrue(response.getMatchingRoute());
        verify(domainsV3).checkReservedRoutes(any());
    }

    @Test
    @DisplayName("도메인 생성")
    void create() {
        Domain domain = new Domain();
        domain.setName("test.domain.com");
        CreateDomainResponse mockResponse = CreateDomainResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .name(TEST_NAME)
                .relationships(DomainRelationships.builder()
                        .organization(ToOneRelationship.builder().build())
                        .build())
                .isInternal(true)
                .build();
        when(domainsV3.create(any())).thenReturn(Mono.just(mockResponse));

        CreateDomainResponse response = domainsServiceV3.create(domain);

        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(TEST_NAME, response.getName());
        verify(domainsV3).create(any());
    }

    @Test
    @DisplayName("도메인 삭제")
    void delete() {
        String guid = "testGuid";
        when(domainsV3.delete(any())).thenReturn(Mono.empty());

        String response = domainsServiceV3.delete(guid);

        assertNull(response);
        verify(domainsV3).delete(any());
    }

    @Test
    @DisplayName("도메인 정보 조회")
    void get() {
        String guid = "testGuid";
        GetDomainResponse mockResponse = GetDomainResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .name(TEST_NAME)
                .relationships(DomainRelationships.builder()
                        .organization(ToOneRelationship.builder().build())
                        .build())
                .isInternal(true)
                .build();
        when(domainsV3.get(any())).thenReturn(Mono.just(mockResponse));

        GetDomainResponse response = domainsServiceV3.get(guid);

        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(TEST_NAME, response.getName());
        verify(domainsV3).get(any());
    }

    @Test
    @DisplayName("도메인 목록 조회")
    void list() {
        List<String> names = Arrays.asList("domain1.com", "domain2.com");
        List<String> owningOrgGuids = Arrays.asList("org1", "org2");
        ListDomainsResponse mockResponse = ListDomainsResponse.builder().build();
        when(domainsV3.list(any())).thenReturn(Mono.just(mockResponse));

        ListDomainsResponse response = domainsServiceV3.list(names, owningOrgGuids);

        assertNotNull(response);
        verify(domainsV3).list(any());
    }

    @Test
    @DisplayName("도메인 공유")
    void share() {
        String guid = "testGuid";
        ShareDomainResponse mockResponse = ShareDomainResponse.builder().build();
        when(domainsV3.share(any())).thenReturn(Mono.just(mockResponse));

        ShareDomainResponse response = domainsServiceV3.share(guid);

        assertNotNull(response);
        verify(domainsV3).share(any());
    }

    @Test
    @DisplayName("도메인 공유 해제")
    void unshare() {
        String guid = "testGuid";
        when(domainsV3.unshare(any())).thenReturn(Mono.empty());

        Void response = domainsServiceV3.unshare(guid);

        assertNull(response);
        verify(domainsV3).unshare(any());
    }

    @Test
    @DisplayName("도메인 정보 업데이트")
    void update() {
        String guid = "testGuid";
        UpdateDomainResponse mockResponse = UpdateDomainResponse.builder()
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .name(TEST_NAME)
                .relationships(DomainRelationships.builder()
                        .organization(ToOneRelationship.builder().build())
                        .build())
                .isInternal(true)
                .build();
        when(domainsV3.update(any())).thenReturn(Mono.just(mockResponse));

        UpdateDomainResponse response = domainsServiceV3.update(guid);

        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(TEST_NAME, response.getName());
        verify(domainsV3).update(any());
    }
}