package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.servicebindings.CreateServiceBindingResponse;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsResponse;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingRelationships;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingType;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.ServiceBinding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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

import static org.mockito.Mockito.when;

/**
 *  Service Binding Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceBindingsServiceV3Test {
    @InjectMocks
    ServiceBindingsServiceV3 serviceBindingsServiceV3;
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

    Params params;
    private static ServiceBinding serviceBinding = null;

    String appGuid;
    String serviceInstanceGuid;
    String serviceBindingGuid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        appGuid = "c93fba93-7ca4-4a96-aa16-8718c5b808fb";
        serviceInstanceGuid = "3b9ea13a-ad86-4676-8dfe-320a34c3205c";
        serviceBindingGuid = "48264d1f-2b44-4fcb-beff-314c86f7760e";

        serviceBinding = new ServiceBinding();
        serviceBinding.setName("test-service-binding");
        serviceBinding.setType(ServiceBindingType.APPLICATION);
        serviceBinding.setRelationships(ServiceBindingRelationships.builder().serviceInstance(ToOneRelationship.builder().data(Relationship.builder().id(serviceInstanceGuid).build()).build()).application(ToOneRelationship.builder().data(Relationship.builder().id(appGuid).build()).build()).build());

        params = new Params();
        params.setClusterToken(adminToken);

        serviceBindingsServiceV3.apiHost = apiHost;
        serviceBindingsServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Service Binding")
    public void test1_createServiceBinding() throws Exception {
        CreateServiceBindingResponse result = serviceBindingsServiceV3.create(serviceBinding);
        Assert.assertNotNull(result.getJobId());
    }

    @Test
    @DisplayName("List Service Bindings")
    public void test2_listServiceBindings() throws Exception {
        List<String> serviceInstanceGuids = new ArrayList<>();
        serviceInstanceGuids.add(serviceInstanceGuid);

        ListServiceBindingsResponse result = serviceBindingsServiceV3.list(null, null, serviceInstanceGuids, null, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Service Binding")
    public void test3_deleteServiceBinding() throws Exception {
        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);
        ListServiceBindingsResponse lists = serviceBindingsServiceV3.list(appGuids , null, null, null, null, null);

        String result = serviceBindingsServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result == null);
    }
}