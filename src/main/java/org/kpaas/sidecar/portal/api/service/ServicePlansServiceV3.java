package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.serviceplans.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class ServicePlansServiceV3 extends Common {
    public Void delete(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlansV3().delete(DeleteServicePlanRequest.builder().servicePlanId(guid).build()).block();
    }

    public GetServicePlanResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlansV3().get(GetServicePlanRequest.builder().servicePlanId(guid).build()).block();
    }

    public ListServicePlansResponse list() {
        return cloudFoundryClient(tokenProvider())
                .servicePlansV3()
                .list(ListServicePlansRequest
                        .builder()
                        .brokerCatalogIds()
                        .names()
                        .organizationIds()
                        .serviceBrokerIds()
                        .serviceBrokerNames()
                        .serviceInstanceIds()
                        .serviceOfferingIds()
                        .serviceOfferingNames()
                        .spaceIds()
                        .build())
                .block();
    }

    public UpdateServicePlanResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlansV3().update(UpdateServicePlanRequest.builder().servicePlanId(guid).build()).block();
    }

    public UpdateServicePlanVisibilityResponse updateVisibility(String guid) {
        return cloudFoundryClient(tokenProvider()).servicePlansV3().updateVisibility(UpdateServicePlanVisibilityRequest.builder().servicePlanId(guid)
                .type(Visibility.ADMIN).build()).block();
    }
}
