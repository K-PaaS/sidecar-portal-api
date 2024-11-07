package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.applications.CreateApplicationResponse;
import org.cloudfoundry.client.v3.applications.GetApplicationResponse;
import org.cloudfoundry.client.v3.applications.ListApplicationsResponse;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.servicebindings.CreateServiceBindingResponse;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsResponse;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingRelationships;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingType;
import org.cloudfoundry.client.v3.serviceinstances.CreateServiceInstanceResponse;
import org.cloudfoundry.client.v3.serviceinstances.ListServiceInstancesResponse;
import org.cloudfoundry.client.v3.serviceinstances.ServiceInstanceRelationships;
import org.cloudfoundry.client.v3.serviceinstances.ServiceInstanceType;
import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.GetSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.cloudfoundry.client.v3.spaces.SpaceRelationships;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.*;

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
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 *  Service Binding Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("ServiceBindingsServiceV3 Test")
public class ServiceBindingsServiceV3Test {
    @InjectMocks
    ServiceBindingsServiceV3 serviceBindingsServiceV3;
    @InjectMocks
    ServiceInstancesServiceV3 serviceInstancesServiceV3;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
    @InjectMocks
    ApplicationsServiceV3 appServiceV3;
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

    private static Organization organization = null;
    private static Space space = null;
    private static Application app = null;
    private static ServiceInstance serviceInstance = null;
    private static ServiceBinding serviceBinding = null;

    private static String orgGuid = null;
    private static String spaceGuid = null;
    private static String appGuid = null;
    private static String serviceInstanceGuid = null;
    private static String serviceBindingGuid = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("service-binding-org");

        // Space 객체 생성
        space = new Space();
        space.setName("service-binding-space");

        // App 객체 생성
        app = new Application();
        app.setName("service-binding-app");

        // ServiceInstance 객체 생성
        serviceInstance = new ServiceInstance();
        serviceInstance.setName("woogie-service-instance");
        serviceInstance.setType(ServiceInstanceType.USER_PROVIDED);

        // Service Binding 객체 생성
        serviceBinding = new ServiceBinding();
        serviceBinding.setName("woogie-service-binding");
        serviceBinding.setType(ServiceBindingType.APPLICATION);

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        spacesServiceV3.apiHost = apiHost;
        spacesServiceV3.tokenKind = tokenKind;

        appServiceV3.apiHost = apiHost;
        appServiceV3.tokenKind = tokenKind;

        serviceInstancesServiceV3.apiHost = apiHost;
        serviceInstancesServiceV3.tokenKind = tokenKind;

        serviceBindingsServiceV3.apiHost = apiHost;
        serviceBindingsServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Service Binding")
    public void test1_createServiceBinding() throws Exception {
        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();
        Assert.assertNotNull(orgResult.getId());

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceResult = spacesServiceV3.create(space);
        spaceGuid = spaceResult.getId();
        Assert.assertNotNull(spaceResult.getId());

        // App 생성
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateApplicationResponse appResult = appServiceV3.create(app);
        appGuid = appResult.getId();
        Assert.assertNotNull(appResult.getId());

        // Service Instance 생성
        serviceInstance.setRelationships(ServiceInstanceRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateServiceInstanceResponse svcInstanceResult = serviceInstancesServiceV3.create(serviceInstance);
        Assert.assertNotNull(svcInstanceResult.getServiceInstance());

        List<String> serviceInstanceNames = new ArrayList<>();
        serviceInstanceNames.add(serviceInstance.getName());

        ListServiceInstancesResponse svcInstanceLists = serviceInstancesServiceV3.list(null, serviceInstanceNames);
        serviceInstanceGuid = svcInstanceLists.getResources().get(0).getId();
        Assert.assertNotNull(svcInstanceLists.getResources().get(0).getId());

        // Service Binding 생성
        serviceBinding.setRelationships(ServiceBindingRelationships.builder().serviceInstance(ToOneRelationship.builder().data(Relationship.builder().id(serviceInstanceGuid).build()).build()).application(ToOneRelationship.builder().data(Relationship.builder().id(appGuid).build()).build()).build());

        CreateServiceBindingResponse result = serviceBindingsServiceV3.create(serviceBinding);
        Assert.assertNotNull(result.getServiceBinding());
    }

    @Test
    @DisplayName("List Service Bindings")
    public void test2_listServiceBindings() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);

        ListServiceBindingsResponse result = serviceBindingsServiceV3.list(appGuids, null, null, null, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Delete Service Binding")
    public void test3_deleteServiceBinding() throws Exception {
        // Service Binding 삭제
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);

        ListServiceBindingsResponse bindLists = serviceBindingsServiceV3.list(appGuids, null, null, null, null, null);
        Assert.assertNotNull(bindLists.getResources().get(0).getId());

        String result = serviceBindingsServiceV3.delete(bindLists.getResources().get(0).getId());
        Assert.assertTrue(result == null);

        // Service Instance 삭제
        List<String> serviceInstanceName = new ArrayList();
        serviceInstanceName.add(serviceInstance.getName());

        ListServiceInstancesResponse svcInstanceLists = serviceInstancesServiceV3.list(null, serviceInstanceName);

        Optional<String> svcInstanceResult = serviceInstancesServiceV3.delete(svcInstanceLists.getResources().get(0).getId());
        Assert.assertNotNull(svcInstanceResult);

        // App 삭제
        String appResult = appServiceV3.delete(appLists.getResources().get(0).getId());
        Assert.assertTrue(appResult.contains(appLists.getResources().get(0).getId()));

        // Space 삭제
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse getSpace = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        Assert.assertNotNull(getSpace.getName());

        String spaceResult = spacesServiceV3.delete(spaceLists.getResources().get(0).getId());
        Assert.assertTrue(spaceResult.contains(spaceLists.getResources().get(0).getId()));

        // Organization 삭제
        List<String> orgNames = new ArrayList<>();
        orgNames.add(organization.getName());

        ListOrganizationsResponse orgLists = organizationsServiceV3.list(orgNames);

        String orgResult = organizationsServiceV3.delete(orgLists.getResources().get(0).getId());
        Assert.assertTrue(orgResult.contains(orgLists.getResources().get(0).getId()));
    }
}