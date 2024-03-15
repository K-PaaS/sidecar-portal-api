package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.serviceofferings.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceOfferingsServiceV3 extends Common {
    public Void delete(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceOfferingsV3().delete(DeleteServiceOfferingRequest.builder().build()).block();
    }

    public GetServiceOfferingResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceOfferingsV3().get(GetServiceOfferingRequest.builder().build()).block();
    }

    public ListServiceOfferingsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceOfferingsV3().list(ListServiceOfferingsRequest.builder().build()).block();
    }

    public UpdateServiceOfferingResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceOfferingsV3().update(UpdateServiceOfferingRequest.builder().build()).block();
    }
}
