package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.processes.*;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Process;
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
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessesServiceTest {
    @InjectMocks
    ProcessesService processesService;
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
    private static Process process = null;
    String appGuid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        appGuid = "33f29649-7bee-40ae-a6c9-d0197334c2c4";

        process = new Process();
        process.setId("cf-proc-33f29649-7bee-40ae-a6c9-d0197334c2c4-web");
        process.setInstances(5);
        process.setDiskInMb(256);
        process.setMemoryInMb(1024);
        process.setCommand("rackup");

        params = new Params();
        params.setClusterToken(adminToken);

        processesService.apiHost = apiHost;
        processesService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Get Process")
    public void test1_getProcess() throws Exception {
        List<String> appGuids = new ArrayList<>();
        appGuids.add(appGuid);
        ListProcessesResponse lists = processesService.list(appGuids, null, null);

        GetProcessResponse result = processesService.get(lists.getResources().get(0).getId());
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Get Process Statistics")
    public void test2_getProcessStatistics() throws Exception {
        String processGuid = process.getId();

        GetProcessStatisticsResponse result = processesService.getStatistics(processGuid);
        Assert.assertNotNull(result.getResources());
    }

    @Test
    @DisplayName("List Processes")
    public void test3_listProcesses() throws Exception {
        ListProcessesResponse result = processesService.list(null, null, null);
        Assert.assertTrue(result.getPagination().getTotalResults() > 0);
    }

    @Test
    @DisplayName("Scale Process")
    public void test4_scaleProcess() throws Exception {
        ScaleProcessResponse result = processesService.scale(process.getId(), process);
        Assert.assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Update Process")
    public void test5_updateProcess() throws Exception {
        UpdateProcessResponse result = processesService.update(process.getId(), process);
        Assert.assertNotNull(result.getUpdatedAt());
    }
}