package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.serviceplanvisibilities.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServicePlanVisibilitiesService extends Common {
    public CreateServicePlanVisibilityResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlanVisibilities().create(CreateServicePlanVisibilityRequest.builder().build()).block();
    }

    public DeleteServicePlanVisibilityResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlanVisibilities().delete(DeleteServicePlanVisibilityRequest.builder().build()).block();
    }

    public GetServicePlanVisibilityResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlanVisibilities().get(GetServicePlanVisibilityRequest.builder().build()).block();
    }

    public ListServicePlanVisibilitiesResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlanVisibilities().list(ListServicePlanVisibilitiesRequest.builder().build()).block();
    }

    public UpdateServicePlanVisibilityResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlanVisibilities().update(UpdateServicePlanVisibilityRequest.builder().build()).block();
    }
}
