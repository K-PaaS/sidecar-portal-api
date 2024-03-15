package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.reactor.TokenProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.RestTemplateService;
import org.kpaas.sidecar.portal.api.model.Batch;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LogService extends Common {
    private final RestTemplateService restTemplateService;

    public LogService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    public Batch getLog(String guid, String time, int limit, boolean isDescending, String envelope_types) {
        TokenProvider tokenProvider = tokenProvider();

        String reqUrl = apiHost + "/api/v1/read/" + guid  + "?" + (isDescending ? "descending=true&" : "") + "envelope_types=" + envelope_types + (limit == 0  ? "" : "&limit=" + limit) +"&start_time=" + time;
        Map logmap = new HashMap();//restTemplateService.cfSend(token, reqUrl, HttpMethod.GET, null, Map.class);


        ObjectMapper mapper = new ObjectMapper();
        Batch batch = mapper.convertValue(logmap.get("envelopes"), Batch.class);
        return batch;
    }

}
