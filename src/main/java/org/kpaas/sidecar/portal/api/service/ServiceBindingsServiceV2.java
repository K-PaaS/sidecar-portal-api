package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.servicebindings.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceBindingsServiceV2 extends Common {
    public CreateServiceBindingResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBindingsV2().create(CreateServiceBindingRequest.builder().build()).block();
    }

    public DeleteServiceBindingResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBindingsV2().delete(DeleteServiceBindingRequest.builder().build()).block();
    }

    public GetServiceBindingResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBindingsV2().get(GetServiceBindingRequest.builder().build()).block();
    }

    public GetServiceBindingParametersResponse getParameters(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBindingsV2().getParameters(GetServiceBindingParametersRequest.builder().build()).block();
    }

    public ListServiceBindingsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBindingsV2().list(ListServiceBindingsRequest.builder().build()).block();
    }
}
