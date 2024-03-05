package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.serviceplanvisibilities.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServicePlanVisibilitiesService extends Common {
    public CreateServicePlanVisibilityResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlanVisibilities().create(CreateServicePlanVisibilityRequest.builder().build()).block();
    }

    public DeleteServicePlanVisibilityResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlanVisibilities().delete(DeleteServicePlanVisibilityRequest.builder().build()).block();
    }

    public GetServicePlanVisibilityResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlanVisibilities().get(GetServicePlanVisibilityRequest.builder().build()).block();
    }

    public ListServicePlanVisibilitiesResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlanVisibilities().list(ListServicePlanVisibilitiesRequest.builder().build()).block();
    }

    public UpdateServicePlanVisibilityResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).servicePlanVisibilities().update(UpdateServicePlanVisibilityRequest.builder().build()).block();
    }
}
