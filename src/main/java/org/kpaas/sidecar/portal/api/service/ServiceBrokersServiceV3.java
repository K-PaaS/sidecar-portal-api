package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.servicebrokers.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceBrokersServiceV3 extends Common {
    public String create(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokersV3().create(CreateServiceBrokerRequest.builder().build()).block();
    }

    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokersV3().delete(DeleteServiceBrokerRequest.builder().build()).block();
    }

    public GetServiceBrokerResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokersV3().get(GetServiceBrokerRequest.builder().build()).block();
    }

    public ListServiceBrokersResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokersV3().list(ListServiceBrokersRequest.builder().build()).block();
    }

    public UpdateServiceBrokerResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokersV3().update(UpdateServiceBrokerRequest.builder().build()).block();
    }
}
