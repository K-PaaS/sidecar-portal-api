package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.serviceplans.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServicePlansService extends Common {
    public DeleteServicePlanResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlans().delete(DeleteServicePlanRequest.builder().build()).block();
    }

    public GetServicePlanResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlans().get(GetServicePlanRequest.builder().build()).block();
    }

    public ListServicePlansResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlans().list(ListServicePlansRequest.builder().build()).block();
    }

    public UpdateServicePlanResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlans().update(UpdateServicePlanRequest.builder().build()).block();
    }
}
