package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.routes.*;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Route;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoutesServiceV3Test {
    private static final Logger log = LoggerFactory.getLogger(RoutesServiceV3Test.class);
    @InjectMocks
    RoutesServiceV3 routesServiceV3;
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
    private static Route route = null;

    String orgGuid;
    String spaceGuid;
    String appGuid;
    String domainGuid;
    String routeGuid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        orgGuid = "cf-org-81050eb7-2bf7-4a5e-9477-a2b0b5960da5";
        spaceGuid = "cf-space-96a0804a-ecd9-4b19-8912-3fd672f987c1";
        appGuid = "b3555ae9-56ba-49b7-8929-0808991cb62e";
        domainGuid = "b640746e-fa15-44b0-8b82-183846702d1d";
        routeGuid = "cf-route-986203e2-a522-49bd-bfb7-da74ba8c5cf4";

        route = new Route();
        route.setHost("test-route");
        route.setPath("/v3/domains");
        route.setRelationships(RouteRelationships.builder().domain(ToOneRelationship.builder().data(Relationship.builder().id("733804cc-5f4e-4658-9ac6-d9aeebdded6c").build()).build()).space(ToOneRelationship.builder().data(Relationship.builder().id("cf-space-96a0804a-ecd9-4b19-8912-3fd672f987c1").build()).build()).build());


        params = new Params();
        params.setClusterToken(adminToken);

        routesServiceV3.apiHost = apiHost;
        routesServiceV3.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Create Route")
    public void test1_createRoute() throws Exception {
        CreateRouteResponse result = routesServiceV3.create(route);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Route")
    public void test2_getRoute() throws Exception {
        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);
        ListRoutesResponse lists = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);

        GetRouteResponse result = routesServiceV3.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Insert Destinations")
    public void test3_insertRouteDestinations() throws Exception {
        InsertRouteDestinationsResponse result = routesServiceV3.insertDestinations(routeGuid, appGuid);
        Assert.assertNotNull(result.getDestinations());
    }

    @Test
    @DisplayName("List Route")
    public void test4_listRoute() throws Exception {
        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);
        ListRoutesResponse result = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("List Route Destinations")
    public void test5_listRouteDestinations() throws Exception {
        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);

        ListRouteDestinationsResponse result = routesServiceV3.listDestinations(routeGuid, appGuids);
        Assert.assertNotNull(result.getDestinations());
    }

    @Test
    @DisplayName("remove Destinations")
    public void test6_removeDestinations() throws Exception {
        String routeGuid = "cf-route-986203e2-a522-49bd-bfb7-da74ba8c5cf4";
        String appGuid = "b3555ae9-56ba-49b7-8929-0808991cb62e";
        Void result = routesServiceV3.removeDestinations(routeGuid, appGuid);
        Assert.assertTrue(result == null);
    }

    @Test
    @DisplayName("Delete Route")
    public void test7_deleteRoute() throws Exception {
        List<String> spaceGuids = new ArrayList<>();
        spaceGuids.add(spaceGuid);

        ListRoutesResponse lists = routesServiceV3.list(null, null, null, null, null, null, spaceGuids);

        String result = routesServiceV3.delete(lists.getResources().get(0).getId());
        Assert.assertTrue(result.contains(lists.getResources().get(0).getId()));
    }
}