package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.applications.CreateApplicationResponse;
import org.cloudfoundry.client.v3.applications.GetApplicationResponse;
import org.cloudfoundry.client.v3.applications.ListApplicationsResponse;
import org.cloudfoundry.client.v3.domains.CreateDomainResponse;
import org.cloudfoundry.client.v3.domains.GetDomainResponse;
import org.cloudfoundry.client.v3.domains.ListDomainsResponse;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.routes.*;
import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.GetSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.cloudfoundry.client.v3.spaces.SpaceRelationships;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.*;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.model.Route;

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
 *  Route Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("RoutesServiceV3 Test")
public class RoutesServiceV3Test {
    @InjectMocks
    RoutesServiceV3 routesServiceV3;
    @InjectMocks
    OrganizationsServiceV3 organizationsServiceV3;
    @InjectMocks
    SpacesServiceV3 spacesServiceV3;
    @InjectMocks
    ApplicationsServiceV3 appServiceV3;
    @InjectMocks
    DomainsServiceV3 domainsServiceV3;
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
    private static Domain domain = null;
    private static Route route = null;

    private static String orgGuid = null;
    private static String spaceGuid = null;
    private static String appGuid = null;
    private static String domainGuid = null;
    private static String routeGuid = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("routes-org");

        // Space 객체 생성
        space = new Space();
        space.setName("routes-space");

        // Domain 객체 생성
        domain = new Domain();
        domain.setName("routes.com");

        // App 객체 생성
        app = new Application();
        app.setName("routes-app");

        // Route 객체 생성
        route = new Route();
        route.setHost("routes-route");
        route.setPath("/v3/domains");

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        spacesServiceV3.apiHost = apiHost;
        spacesServiceV3.tokenKind = tokenKind;

        domainsServiceV3.apiHost = apiHost;
        domainsServiceV3.tokenKind = tokenKind;

        appServiceV3.apiHost = apiHost;
        appServiceV3.tokenKind = tokenKind;

        routesServiceV3.apiHost = apiHost;
        routesServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Route")
    public void test1_createRoute() throws Exception {
        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceResult = spacesServiceV3.create(space);
        spaceGuid = spaceResult.getId();

        // Domain 생성
        CreateDomainResponse domainResult = domainsServiceV3.create(domain);
        domainGuid = domainResult.getId();
        Assert.assertNotNull(domainResult.getId());

        // Route 생성
        route.setRelationships(RouteRelationships.builder().domain(ToOneRelationship.builder().data(Relationship.builder().id(domainGuid).build()).build()).space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateRouteResponse routeResult = routesServiceV3.create(route);
        Assert.assertNotNull(routeResult.getId());

        // App 생성
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateApplicationResponse appResult = appServiceV3.create(app);
        Assert.assertNotNull(appResult.getId());
    }

    @Test
    @DisplayName("Get Route")
    public void test2_getRoute() throws Exception {
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse spaceGet = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        spaceGuid = spaceGet.getId();
        Assert.assertNotNull(spaceGet.getId());

        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);

        ListRoutesResponse lists = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);

        GetRouteResponse result = routesServiceV3.get(lists.getResources().get(0).getId());
        routeGuid = result.getId();
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Insert Destinations")
    public void test3_insertRouteDestinations() throws Exception {
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse spaceGet = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        spaceGuid = spaceGet.getId();

        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);

        ListRoutesResponse routeLists = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);

        GetRouteResponse routeGet = routesServiceV3.get(routeLists.getResources().get(0).getId());

        String routeGuid = routeGet.getId();
        Assert.assertNotNull(routeGet.getId());

        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        InsertRouteDestinationsResponse result = routesServiceV3.insertDestinations(routeGuid, appGuid);
        Assert.assertNotNull(result.getDestinations());
    }

    @Test
    @DisplayName("List Route")
    public void test4_listRoute() throws Exception {
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse spaceGet = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        spaceGuid = spaceGet.getId();

        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);

        ListRoutesResponse result = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("List Route Destinations")
    public void test5_listRouteDestinations() throws Exception {
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse spaceGet = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        spaceGuid = spaceGet.getId();

        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);

        ListRoutesResponse routeLists = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);

        GetRouteResponse routeGet = routesServiceV3.get(routeLists.getResources().get(0).getId());

        String routeGuid = routeGet.getId();
        Assert.assertNotNull(routeGet.getId());

        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appResult.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);

        ListRouteDestinationsResponse result = routesServiceV3.listDestinations(routeGuid, appGuids);
        Assert.assertNotNull(result.getDestinations());
    }

    @Test
    @DisplayName("remove Destinations")
    public void test6_removeDestinations() throws Exception {
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

        GetSpaceResponse spaceGet = spacesServiceV3.get(spaceLists.getResources().get(0).getId());
        spaceGuid = spaceGet.getId();

        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);

        ListRoutesResponse routeLists = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);

        GetRouteResponse routeGet = routesServiceV3.get(routeLists.getResources().get(0).getId());

        String routeGuid = routeGet.getId();
        Assert.assertNotNull(routeGet.getId());

        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appResult = appServiceV3.get(appLists.getResources().get(0).getId());
        String appGuid = appResult.getId();

        Void result = routesServiceV3.removeDestinations(routeGuid, appGuid);
        Assert.assertTrue(result == null);
    }

    @Test
    @DisplayName("Delete Route")
    public void test7_deleteRoute() throws Exception {
        // Route 삭제
        List<String> domainNames = new ArrayList<>();
        domainNames.add(domain.getName());

        ListDomainsResponse domainLists = domainsServiceV3.list(domainNames, null);

        GetDomainResponse getDomain = domainsServiceV3.get(domainLists.getResources().get(0).getId());
        domainGuid = getDomain.getId();

        List<String> domainGuids = new ArrayList<>();
        domainGuids.add(domainGuid);

        ListRoutesResponse routeLists = routesServiceV3.list(null, domainGuids, null, null, null, null, null);

        String routeResult = routesServiceV3.delete(routeLists.getResources().get(0).getId());
        Assert.assertTrue(routeResult.contains(routeLists.getResources().get(0).getId()));

        // Domain 삭제
        String domainResult = domainsServiceV3.delete(domainLists.getResources().get(0).getId());
        Assert.assertTrue(domainResult.contains(domainLists.getResources().get(0).getId()));

        // App 삭제
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        String appResult = appServiceV3.delete(appLists.getResources().get(0).getId());
        Assert.assertTrue(appResult.contains(appLists.getResources().get(0).getId()));

        // Space 삭제
        List<String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);

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