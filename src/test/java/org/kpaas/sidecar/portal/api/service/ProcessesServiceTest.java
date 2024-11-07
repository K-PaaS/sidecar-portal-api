package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.applications.CreateApplicationResponse;
import org.cloudfoundry.client.v3.applications.GetApplicationResponse;
import org.cloudfoundry.client.v3.applications.ListApplicationsResponse;
import org.cloudfoundry.client.v3.organizations.CreateOrganizationResponse;
import org.cloudfoundry.client.v3.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v3.processes.*;
import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.cloudfoundry.client.v3.spaces.SpaceRelationships;

import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.model.Organization;
import org.kpaas.sidecar.portal.api.model.Process;
import org.kpaas.sidecar.portal.api.model.Space;

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
 *  Processes Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.11.07
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("ProcessesService Test")
public class ProcessesServiceTest {
    @InjectMocks
    ProcessesService processesService;
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
    private static Process process = null;

    private static String orgGuid = null;
    private static String spaceGuid = null;
    private static String appGuid = null;
    private static String processGuid = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Organization 객체 생성
        organization = new Organization();
        organization.setName("processes-org");

        // Space 객체 생성
        space = new Space();
        space.setName("processes-space");

        // App 객체 생성
        app = new Application();
        app.setName("processes-app");

        // Process 객체 생성
        process = new Process();
        process.setInstances(5);
        process.setDiskInMb(256);
        process.setMemoryInMb(1024);
        process.setCommand("rackup");

        params = new Params();
        params.setClusterToken(adminToken);

        organizationsServiceV3.apiHost = apiHost;
        organizationsServiceV3.tokenKind = tokenKind;

        spacesServiceV3.apiHost = apiHost;
        spacesServiceV3.tokenKind = tokenKind;

        appServiceV3.apiHost = apiHost;
        appServiceV3.tokenKind = tokenKind;

        processesService.apiHost = apiHost;
        processesService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Get Process")
    public void test1_getProcess() throws Exception {
        // Organization 생성
        CreateOrganizationResponse orgResult = organizationsServiceV3.create(organization);
        orgGuid = orgResult.getId();

        // Space 생성
        space.setRelationships(SpaceRelationships.builder().organization(ToOneRelationship.builder().data(Relationship.builder().id(orgGuid).build()).build()).build());
        CreateSpaceResponse spaceResult = spacesServiceV3.create(space);
        spaceGuid = spaceResult.getId();

        // App 생성
        app.setRelationships(ApplicationRelationships.builder().space(ToOneRelationship.builder().data(Relationship.builder().id(spaceGuid).build()).build()).build());
        CreateApplicationResponse appResult = appServiceV3.create(app);
        Assert.assertNotNull(appResult.getId());

        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);
        process.setId("cf-proc-" + appGuid + "-web");

        ListProcessesResponse lists = processesService.list(appGuids, null, null);

        GetProcessResponse result = processesService.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Process Statistics")
    public void test2_getProcessStatistics() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListProcessesResponse lists = processesService.list(appGuids, null, null);
        processGuid = lists.getResources().get(0).getId();
        Assert.assertTrue(lists.getPagination().getTotalResults() > 0);

        GetProcessStatisticsResponse result = processesService.getStatistics(processGuid);
        Assert.assertNotNull(result.getResources());
    }

    @Test
    @DisplayName("List Processes")
    public void test3_listProcesses() throws Exception {
        // App 이름 정보 가져오기
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        // App List 가져오기
        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        // App 조회정보 가져오기
        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListProcessesResponse result = processesService.list(appGuids, null, null);
        processGuid = result.getResources().get(0).getId();
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Scale Process")
    public void test4_scaleProcess() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListProcessesResponse processLists = processesService.list(appGuids, null, null);
        processGuid = processLists.getResources().get(0).getId();

        ScaleProcessResponse result = processesService.scale(processGuid, process);
        Assert.assertNotNull(result.getInstances());
    }

    @Test
    @DisplayName("Update Process")
    public void test5_updateProcess() throws Exception {
        List<String> appNames = new ArrayList<>();
        appNames.add(app.getName());

        ListApplicationsResponse appLists = appServiceV3.list(appNames, null, null);

        GetApplicationResponse appGet = appServiceV3.get(appLists.getResources().get(0).getId());
        appGuid = appGet.getId();

        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGet.getId());

        ListProcessesResponse processLists = processesService.list(appGuids, null, null);
        processGuid = processLists.getResources().get(0).getId();

        UpdateProcessResponse result = processesService.update(processGuid, process);
        Assert.assertNotNull(result.getCommand());

        // App 삭제
        String appResult = appServiceV3.delete(appLists.getResources().get(0).getId());
        Assert.assertTrue(appResult.contains(appLists.getResources().get(0).getId()));

        // Space 삭제
        List <String> spaceNames = new ArrayList<>();
        spaceNames.add(space.getName());

        ListSpacesResponse spaceLists = spacesServiceV3.list(null, spaceNames);
        String spaceResult = spacesServiceV3.delete(spaceLists.getResources().get(0).getId());
        Assert.assertTrue(spaceResult.contains(spaceLists.getResources().get(0).getId()));

        // Organization 삭제
        List<String> orgNames = new ArrayList<>();
        orgNames.add(organization.getName());

        ListOrganizationsResponse orgLists = organizationsServiceV3.list(orgNames);
        String orgResult =organizationsServiceV3.delete(orgLists.getResources().get(0).getId());
        Assert.assertTrue(orgResult.contains(orgLists.getResources().get(0).getId()));
    }
}