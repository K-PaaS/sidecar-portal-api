package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.servicebrokers.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceBrokersService extends Common {
    public CreateServiceBrokerResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokers().create(CreateServiceBrokerRequest.builder().build()).block();
    }

    public Void delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokers().delete(DeleteServiceBrokerRequest.builder().build()).block();
    }

    public GetServiceBrokerResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokers().get(GetServiceBrokerRequest.builder().build()).block();
    }

    public ListServiceBrokersResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokers().list(ListServiceBrokersRequest.builder().build()).block();
    }

    public UpdateServiceBrokerResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBrokers().update(UpdateServiceBrokerRequest.builder().build()).block();
    }

}
