package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.serviceinstances.*;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 *  ServiceInstance Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceInstancesServiceV3Test {
    @InjectMocks
    ServiceInstancesServiceV3 serviceInstancesServiceV3;
    @Mock
    AuthUtil authUtill;

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

    Params params;
    private static ServiceInstance serviceInstance = null;

    String orgGuid;
    String spaceGuid;
    String appGuid;
    String domainGuid;
    String serviceInstanceGuid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        orgGuid = "cf-org-81050eb7-2bf7-4a5e-9477-a2b0b5960da5";
        spaceGuid = "cf-space-96a0804a-ecd9-4b19-8912-3fd672f987c1";
        appGuid = "c93fba93-7ca4-4a96-aa16-8718c5b808fb";
        domainGuid = "733804cc-5f4e-4658-9ac6-d9aeebdded6c";
        serviceInstanceGuid = "3b9ea13a-ad86-4676-8dfe-320a34c3205c";

        serviceInstance = new ServiceInstance();
        serviceInstance.setName("service-instance-test");
        serviceInstance.setType(ServiceInstanceType.USER_PROVIDED);
        serviceInstance.setRelationships(ServiceInstanceRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id("cf-space-96a0804a-ecd9-4b19-8912-3fd672f987c1").build()).build()).build());
        serviceInstance.setId(serviceInstanceGuid);

        params = new Params();
        params.setClusterToken(adminToken);

        serviceInstancesServiceV3.apiHost = apiHost;
        serviceInstancesServiceV3.tokenKind = tokenKind;

        when(authUtill.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Service Instance")
    public void test1_createServiceInstance() throws Exception {
        CreateServiceInstanceResponse result = serviceInstancesServiceV3.create(serviceInstance);
        Assert.assertNotNull(result.getJobId());
    }

    @Test
    @DisplayName("List Service Instance")
    public void test2_listServiceInstance() throws Exception {
        ListServiceInstancesResponse result = serviceInstancesServiceV3.list(null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Update Service Instance")
    public void test3_updateServiceInstance() throws Exception {
        UpdateServiceInstanceResponse result = serviceInstancesServiceV3.update(serviceInstance);
        Assert.assertNotNull(result.getServiceInstance());
    }

    @Test
    @DisplayName("Delete Service Instance")
    public void test4_deleteServiceInstance() throws Exception {
        List<String> spaceGuids = new ArrayList();
        spaceGuids.add(spaceGuid);
        List<String> serviceInstanceName = new ArrayList();
        serviceInstanceName.add(serviceInstance.getName());

        ListServiceInstancesResponse lists = serviceInstancesServiceV3.list(spaceGuids, serviceInstanceName);

        Optional<String> result = serviceInstancesServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertNotNull(result);
    }
}