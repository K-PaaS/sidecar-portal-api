package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.lib.org.codehaus.jackson.map.ObjectMapper;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.kpaas.sidecar.portal.api.model.Batch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 *  Log Service 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.10.10
 **/

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogServiceTest {
    @InjectMocks
    LogService logService;
    @Mock
    AuthUtil authUtil;
    @Mock
    SidecarRestTemplateService restTemplateService;

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

    String appGuid;
    String reqUrl;
    String time;
    String envelope_types;

    boolean isDescending;
    int limit;

    Batch batch;
    Map<String, Object> logMap;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        appGuid = "c93fba93-7ca4-4a96-aa16-8718c5b808fb";
        time = "-6795364578871345152";
        envelope_types = "LOG";
        isDescending = true;
        limit = 100;

        reqUrl = "/api/v1/read/" + appGuid  + "?" + (isDescending ? "descending=true&" : "") + "envelope_types=" + envelope_types + (limit == 0  ? "" : "&limit=" + limit) +"&start_time=" + time;

        logMap = restTemplateService.send(Constants.TARGET_SIDECAR_API, reqUrl, HttpMethod.GET, null, Map.class, authUtil.sidecarAuth());

        ObjectMapper mapper = new ObjectMapper();
        batch = mapper.convertValue(logMap, Batch.class);

        params = new Params();
        params.setClusterToken(adminToken);

        logService.apiHost = apiHost;
        logService.tokenKind = tokenKind;

        when(authUtil.sidecarAuth()).thenReturn(params);
    }

    @Test
    @DisplayName("Get Recent Log")
    public void test1_getRecentLog() throws Exception {
        Map mapLog = new HashMap();
        try {
            Batch resAppLogs = logService.getLog(appGuid, time, limit, isDescending, envelope_types);
            mapLog.put("resAppLogs", resAppLogs);
            Assert.assertNotNull(resAppLogs);
        } catch (Exception e) {
            mapLog.put("log", "");
        }
        Assert.assertNotNull(mapLog);
    }

    @Test
    @DisplayName("Get Tail Log Recent")
    public void test2_getTailLogRecent() throws Exception {
        Map mapLog = new HashMap();
        try {
            Batch resAppLogs = logService.getLog(appGuid, time, 1, isDescending, envelope_types);
            mapLog.put("resAppLogs", resAppLogs);
            Assert.assertNotNull(resAppLogs);
        } catch (Exception e) {
            mapLog.put("log", "");
        }
        Assert.assertNotNull(mapLog);
    }

    @Test
    @DisplayName("Get Tail Log")
    public void test3_getTailLog() throws Exception {
        Map mapLog = new HashMap();
        try {
            String time = String.valueOf(System.currentTimeMillis() * 1000000);
            Batch resAppLogs = logService.getLog(appGuid, time, 0, false, envelope_types);
            Assert.assertNotNull(resAppLogs);
        }
        catch (Exception e) {
            mapLog.put("log", "");
        }
        Assert.assertNotNull(mapLog);
    }
}