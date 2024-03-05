package org.kpaas.sidecar.portal.api.controller;

import org.kpaas.sidecar.portal.api.model.Batch;
import org.kpaas.sidecar.portal.api.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LogController {

    @Autowired
    private LogService logService;
    /**
     * 앱 최근 로그
     *
     * @param app guid
     * @return Map map
     * @throws Exception the exception
     */
    @GetMapping(value = {"/logs/{appGuid}/recentlogs"})
    public Map getRecentLog(@PathVariable String appGuid, String token) throws Exception {
        Map mapLog = new HashMap();
        try {
            Batch respAppLogs = logService.getLog(appGuid, "-6795364578871345152", 100, true, "LOG", token);
            mapLog.put("log", respAppLogs);
        } catch (Exception e) {
            mapLog.put("log", "");
        }
        return mapLog;
    }

    /**
     * 앱 최신 로그
     *
     * @param app guid
     * @return Map map
     * @throws Exception the exception
     */
    @GetMapping(value = {"/logs/{appGuid}/taillogs/recent"})
    public Map getTailLog(@PathVariable String appGuid, String token) throws Exception {
        Map mapLog = new HashMap();
        try {
            Batch respAppLogs = logService.getLog(appGuid, "-6795364578871345152", 1, true, "LOG", token);
            mapLog.put("log", respAppLogs);
        } catch (Exception e) {
            mapLog.put("log", "");
        }
        return mapLog;
    }


    /**
     * 앱 실시간 로그
     *
     * @param guid
     * @return Space respSpace
     * @throws Exception the exception
     */
    @GetMapping(value = {"/logs/{appGuid}/taillogs/{time}"})
    public Map getTailLog(@PathVariable String appGuid, @PathVariable String time, String token) throws Exception {
        Map mapLog = new HashMap();
        try {
            Batch respAppLogs = logService.getLog(appGuid, time,0, false, "LOG", token);
            mapLog.put("log", respAppLogs);
        } catch (Exception e) {
            mapLog.put("log", "");
        }
        return mapLog;
    }

}
