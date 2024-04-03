package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.reactor.TokenProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
import org.kpaas.sidecar.portal.api.model.Batch;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LogService extends Common {
    private final SidecarRestTemplateService restTemplateService;

    public LogService(SidecarRestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    public Batch getLog(String guid, String time, int limit, boolean isDescending, String envelope_types) {
        TokenProvider tokenProvider = tokenProvider();

        String reqUrl = "/api/v1/read/" + guid  + "?" + (isDescending ? "descending=true&" : "") + "envelope_types=" + envelope_types + (limit == 0  ? "" : "&limit=" + limit) +"&start_time=" + time;
        Map logmap = restTemplateService.send(Constants.TARGET_SIDECAR_API, reqUrl, HttpMethod.GET, null, Map.class, authUtil.sidecarAuth());

        ObjectMapper mapper = new ObjectMapper();
        Batch batch = mapper.convertValue(logmap.get("envelopes"), Batch.class);
        return batch;
    }

}
