package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.services.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServicesService extends Common {
    public DeleteServiceResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).services().delete(DeleteServiceRequest.builder().build()).block();
    }

    public GetServiceResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).services().get(GetServiceRequest.builder().build()).block();
    }

    public ListServicesResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).services().list(ListServicesRequest.builder().build()).block();
    }

    public ListServiceServicePlansResponse listServicePlans(String guid) {
        return cloudFoundryClient(tokenProvider()).services().listServicePlans(ListServiceServicePlansRequest.builder().build()).block();
    }
}
