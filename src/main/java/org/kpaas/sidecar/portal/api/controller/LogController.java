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

    String token = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURJRENDQWdpZ0F3SUJBZ0lIRFRENko2QVlCREFOQmdrcWhraUc5dzBCQVFzRkFEQVZNUk13RVFZRFZRUUQKRXdwcmRXSmxjbTVsZEdWek1CNFhEVEkwTURFd05EQXhNRGMwTVZvWERUSTFNREV3TXpBeE1USTBNbG93TkRFWApNQlVHQTFVRUNoTU9jM2x6ZEdWdE9tMWhjM1JsY25NeEdUQVhCZ05WQkFNVEVHdDFZbVZ5Ym1WMFpYTXRZV1J0CmFXNHdnZ0VpTUEwR0NTcUdTSWIzRFFFQkFRVUFBNElCRHdBd2dnRUtBb0lCQVFDNkZ0MG9qaTI0RTdMOFkxY3oKU015bWRwRmFtdHIvQjJIZkM4cDV2WENCZ0pOTE1BeHE0c2V1QStXVGEwdkhjYllKWTVxY1Z2ekY1YmViWHZKSApHeURha0VCb3V6d2JUeWloZlRRdGFubStIc2gwQ0tqcFFXUm5tSmpDVnBJcHc4emlLRngzU2tHaFU1ZXlEN3lNCnRDdjM5WDAxekpzbEZyeFp1bjNCM0N6MFR5MVZCVFRnYVB1bDZPRWd6TlRDakVRQXBGOS9MbzhpTk5vdmZqaUgKK1ZxRmtsa3R1R1UrWVhnMTBZM3JQbEp1a0J3Q0tQTWhZdE9jN3hsdDJCeTR4RWh2OTYvR041UHEyNENhZkhsRApkN0dhcjF6QU0vVmFPRENkdytwWFNJWE9uaGZ0cTBlSWJZaEg4QzNQcnJoNkt3UHd4NFZycUpYdXhVK1dqVWl5CkJuR3pBZ01CQUFHalZqQlVNQTRHQTFVZER3RUIvd1FFQXdJRm9EQVRCZ05WSFNVRUREQUtCZ2dyQmdFRkJRY0QKQWpBTUJnTlZIUk1CQWY4RUFqQUFNQjhHQTFVZEl3UVlNQmFBRkJWTGZGTklJZnZkKzhobTlyZ2xJY1NhVDVQOQpNQTBHQ1NxR1NJYjNEUUVCQ3dVQUE0SUJBUUI0aE8rZTNEcXIyUDVpRGFlSmZxNGUzTWV3ZGY1clZFbEQyRjM1CkllQ2tkK0hscmgwOUJpeDVZdGV4dEFPczRUVjB3L2U5ek9paUZOVUJvL1dRYWhEMkE1Wnh6Y3VBanlpSTFnM2sKRDlPT0xjdTgvY2dHMks4SktORmhBUUtXanczWnJTR214Z0pTc1FQWUNwVU9QWWQvajFkMHQ4WTlGM1pFb3loSAowRUtrMVF5OGhsYnl4UGtRWW91VXA0Z2NScTkzakVIYjM3dDdvaGsvbkUyNnhzK3NNNmd0eHFhbVZ4ei9iRzZoCnAvejhMb3IxTWZxNWVGSzlGSEg2WVgwVk9vYmY5WkpnVEdIdzFWQ1Z0dFFBYUJWUWtka29SRHhnWGxxMEIrWjEKTGhnbzcyRllVMmQ5Y3VhQVJvbTZzZlNLN1h0VHZLbDd6MEpyTW1kZ2JubFNiN1JrCi0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0KLS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFcEFJQkFBS0NBUUVBdWhiZEtJNHR1Qk95L0dOWE0wak1wbmFSV3ByYS93ZGgzd3ZLZWIxd2dZQ1RTekFNCmF1TEhyZ1BsazJ0THgzRzJDV09hbkZiOHhlVzNtMTd5UnhzZzJwQkFhTHM4RzA4b29YMDBMV3A1dmg3SWRBaW8KNlVGa1o1aVl3bGFTS2NQTTRpaGNkMHBCb1ZPWHNnKzhqTFFyOS9WOU5jeWJKUmE4V2JwOXdkd3M5RTh0VlFVMAo0R2o3cGVqaElNelV3b3hFQUtSZmZ5NlBJalRhTDM0NGgvbGFoWkpaTGJobFBtRjROZEdONno1U2JwQWNBaWp6CklXTFRuTzhaYmRnY3VNUkliL2V2eGplVDZ0dUFtbng1UTNleG1xOWN3RFAxV2pnd25jUHFWMGlGenA0WDdhdEgKaUcySVIvQXR6NjY0ZWlzRDhNZUZhNmlWN3NWUGxvMUlzZ1p4c3dJREFRQUJBb0lCQUhDbkNyZFVhd1QybC9JRwpTWEc0Ym51UFo2c0w5K3RQa3NZdUl5TFR0ZG9QOWhzY09VZ3poV0lvNWIvNndtTTR5VlNod2FWSFc3cUp1RDhNCm1XckdWM3M0dkdVcWc5YWNjeUpmNDBoakh0UWpXb0kwQTkxQlF3RWI5YzRnRGxmT09xZm1leU9VQm1zZGFjWFgKbFBNdzF6VkNuY3dGMHgzK3g0TWlUS01oSU8yOFFJT1IwVFFCalVOdGh4d2Qvd3VMTE52c1ZtY3k2RnBRNWlKWgpiaExUS2ZYUDZFbmdHUi9za2JDclJ5QWNXNmJtWnVVeGoyTlVYOEQzRjh3ZHpsMGMvaTh5OU9jbXQzbGhnZGp0Ckdncm1TZFRLOWlvemc5b2tQamRBbHdOTy9ZSngzNlorN1FKVnRnSEdxb0R5RUc5ek15dWY3MmIwUTJwSGhOR0sKN28xclFjRUNnWUVBMVA1bnlVNzd6V2JFVkFpYnE5azFxUTFsWjlkQ2JmclkzelZkZzIxQ1JpQVVXVTlIZ3ZVZQpCS1BzbWVFdHZUVjBEZS8zVXg2QWxuSjB6bzJNOSs4Z3lZZXRRbVhpZEJ0VXBLRlduemcwN3JwTVgrVnJCeUpoClJGd0tWeEZ0UHNpeHR2RHFOUFpFZ0Q3NkJERWJaa2NCdi85clQ2VlFJVlNQU3NHbkVJRFg0akVDZ1lFQTM2bkcKRkZTSTlSSUZhNTdxT2JlTWVBamF1eFVBV3RUOWlaSTdJbTkwbVp3OG53ZWxDa3JHZmp3SHEyWm5sMVRYcS9meApHR3g1dG9FeDdhRGhTdGJPVzcxU2NOSS92SUV5N2RGNzc1aDY0WklwekhjeW5GWkU4enFSK3dGZVp0TThYVkhpCnpBWHFRaVo3K1h4U1M4QVoya1VZUWZibG4zR3V4MGV0OXhpY2xTTUNnWUVBbEJyUENtRThvSWc4TG43SjJJWXkKVlBuMnNHMHpTUXhycjFJSlUzRXU2MW8xTENEL2hlVngyU0l2VEpVUzNBV0dUSWEzUVBudHlpK09YMjU0UHBMdQpCcFNzZW9Za0lENDg3amt0NVlzTTY0OXB1aWpwOGswS3U2RVFvNWgva1crMmU1SXNHV1hCSGpnc1c2dlY5Sm1kCmN1ZGx0RDBzYWZERnJ1TFhpNWM4QnBFQ2dZQWRNeUQxRG9SQzJuTnl0WENHMEh1NlFBMnVWdk9RNkRXaGZFSFUKSVJEcjdRWE5EVVp5clloMGVvblZ5cXpEaVFRUEkzT0thTDhMSUpyUUZ5elNoSEMwY1djTjY2VCtpeStHOU01bApYVmJObERONHlpY0ZXZzN1WXpzanRlNEpKcEsrN0MzYnpQbmtZUG5XZkhDU0N5WlU1S2hpRkVXdUp6MVdMdmw3ClJPOU1XUUtCZ1FESkpySEN2cDhySDRCVWNQa2RndjJxUTlCajJuR2s0ZUR5S2t4UVlFWkxOVDVuVmRrMWNLMSsKMFExQlZ2VUd3aU5YNDlGcTFWY2lkMVJkeUt4ZU5ZWWpVL3FmaUZ6UWNrUkNxTzZyT2l2SFZQVG5QbEFBMERUeQphL3laWU1MaXBrUnZBY09JK1VZeEFrTDdCcUdtUHcyUEFzTWU1YmNrYUU2NlI2NFdjV1U0b3c9PQotLS0tLUVORCBSU0EgUFJJVkFURSBLRVktLS0tLQo=";
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
            Batch respAppLogs = logService.getLog(appGuid, "-6795364578871345152", 100, true, "LOG", this.token);
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
            Batch respAppLogs = logService.getLog(appGuid, "-6795364578871345152", 1, true, "LOG", this.token);
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
            Batch respAppLogs = logService.getLog(appGuid, time,0, false, "LOG", this.token);
            mapLog.put("log", respAppLogs);
        } catch (Exception e) {
            mapLog.put("log", "");
        }
        return mapLog;
    }

}
