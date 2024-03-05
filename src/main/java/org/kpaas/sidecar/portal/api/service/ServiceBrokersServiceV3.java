package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.servicebrokers.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceBrokersServiceV3 extends Common {
    public String create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokersV3().create(CreateServiceBrokerRequest.builder().build()).block();
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokersV3().delete(DeleteServiceBrokerRequest.builder().build()).block();
    }

    public GetServiceBrokerResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokersV3().get(GetServiceBrokerRequest.builder().build()).block();
    }

    public ListServiceBrokersResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokersV3().list(ListServiceBrokersRequest.builder().build()).block();
    }

    public UpdateServiceBrokerResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokersV3().update(UpdateServiceBrokerRequest.builder().build()).block();
    }
}
