package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.serviceplans.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class ServicePlansServiceV3 extends Common {
    public Void delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlansV3().delete(DeleteServicePlanRequest.builder().build()).block();
    }

    public GetServicePlanResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlansV3().get(GetServicePlanRequest.builder().build()).block();
    }

    public ListServicePlansResponse list(String token) {
        return cloudFoundryClient(tokenProvider(token))
                .servicePlansV3()
                .list(ListServicePlansRequest
                        .builder()
                        .build())
                .block();
    }

    public UpdateServicePlanResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlansV3().update(UpdateServicePlanRequest.builder().build()).block();
    }

    public UpdateServicePlanVisibilityResponse updateVisibility(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlansV3().updateVisibility(UpdateServicePlanVisibilityRequest.builder().build()).block();
    }
}
