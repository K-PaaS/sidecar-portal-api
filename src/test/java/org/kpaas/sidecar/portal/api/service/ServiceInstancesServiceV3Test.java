package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.serviceinstances.*;
import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.GetSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.cloudfoundry.client.v3.spaces.SpaceRelationships;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.*;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;

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
 *  ServiceInstance Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("ServiceInstancesServiceV3 Test")
public class ServiceInstancesServiceV3Test {
    @InjectMocks
    ServiceInstancesServiceV3 serviceInstancesServiceV3;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
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
    private static ServiceInstance serviceInstance = null;

    private static String orgGuid = null;
    private static String spaceGuid = null;
    private static String serviceInstanceGuid = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("service-instance-org");

        // Space 객체 생성
        space = new Space();
        space.setName("service-instance-space");

        // ServiceInstance 객체 생성
        serviceInstance = new ServiceInstance();
        serviceInstance.setName("woogie-service-instance");
        serviceInstance.setType(ServiceInstanceType.USER_PROVIDED);

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        spacesServiceV3.apiHost = apiHost;
        spacesServiceV3.tokenKind = tokenKind;

        serviceInstancesServiceV3.apiHost = apiHost;
        serviceInstancesServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Service Instance")
    public void test1_createServiceInstance() throws Exception {

        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceResult = spacesServiceV3.create(space);
        spaceGuid = spaceResult.getId();

        // Service Instance 생성
        serviceInstance.setRelationships(ServiceInstanceRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateServiceInstanceResponse result = serviceInstancesServiceV3.create(serviceInstance);
        Assert.assertNotNull(result.getServiceInstance());
    }

    @Test
    @DisplayName("List Service Instance")
    public void test2_listServiceInstance() throws Exception {
        List<String> serviceInstanceName = new ArrayList<>();
        serviceInstanceName.add(serviceInstance.getName());

        ListServiceInstancesResponse result = serviceInstancesServiceV3.list(null, serviceInstanceName);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Update Service Instance")
    public void test3_updateServiceInstance() throws Exception {
        List<String> serviceInstanceName = new ArrayList<>();
        serviceInstanceName.add(serviceInstance.getName());

        ListServiceInstancesResponse lists = serviceInstancesServiceV3.list(null, serviceInstanceName);
        serviceInstanceGuid = lists.getResources().get(0).getId();
        serviceInstance.setId(serviceInstanceGuid);

        UpdateServiceInstanceResponse result = serviceInstancesServiceV3.update(serviceInstance);
        Assert.assertNotNull(result.getServiceInstance());
    }

    @Test
    @DisplayName("Delete Service Instance")
    public void test4_deleteServiceInstance() throws Exception {
        // Space 정보 가져오기
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse getSpace = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        Assert.assertNotNull(getSpace.getName());

        // Service Instance 삭제
        List<String> serviceInstanceName = new ArrayList();
        serviceInstanceName.add(serviceInstance.getName());

        ListServiceInstancesResponse lists = serviceInstancesServiceV3.list(null, serviceInstanceName);

        Optional<String> result = serviceInstancesServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertNotNull(result);

        // Space 삭제
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