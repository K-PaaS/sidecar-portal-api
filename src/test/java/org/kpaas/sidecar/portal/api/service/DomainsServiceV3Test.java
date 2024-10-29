package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.domains.CreateDomainResponse;
import org.cloudfoundry.client.v3.domains.GetDomainResponse;
import org.cloudfoundry.client.v3.domains.ListDomainsResponse;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Domain;
import org.junit.jupiter.api.DisplayName;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 *  Domain Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DomainsServiceV3Test {
    @InjectMocks
    DomainsServiceV3 domainServiceV3;
    @Mock
    AuthUtil authUtil;

    @Value("${sidecar.apiHost}")
    private String apiHost;

    @Value("${sidecar.tokenKind}")
    private String tokenKind;

    @Value("${sidecar.test.admin-token}")
    private String adminToken;

    @Value("${sidecar.test.org-manager-token}")
    private String orgManagerToken;

    @Value("${sidecar.test.org-user-token}")
    private String orgUserToken;

    @Value("${sidecar.test.space-developer-token}")
    private String spaceDeveloperToken;

    @Value("${sidecar.test.space-user-token}")
    private String spaceUserToken;

    @Value("${sidecar.test.domain-guid}")
    private String domainGuid;

    Params params;
    private static Domain domain = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        domain = new Domain();
        domain.setName("unit-test.com");

        params = new Params();
        params.setClusterToken(adminToken);

        domainServiceV3.apiHost = apiHost;
        domainServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Domain")
    public void test1_createDomain() throws Exception {
        CreateDomainResponse result = domainServiceV3.create(domain);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Domains")
    public void test2_getDomains() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(domain.getName());
        ListDomainsResponse lists = domainServiceV3.list( names , null);

        GetDomainResponse result = domainServiceV3.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getName());
    }

    @Test
    @DisplayName("List Domains")
    public void test3_listDomains() throws Exception {
        ListDomainsResponse result = domainServiceV3.list(null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Domain")
    public void test4_deleteDomain() throws Exception {
        List<String> names = new ArrayList<>();
        names.add(domain.getName());
        ListDomainsResponse lists = domainServiceV3.list( names , null);

        String result = domainServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result.contains(lists.getResources().get(0).getId()));
    }
}