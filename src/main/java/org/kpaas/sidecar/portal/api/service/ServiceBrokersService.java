package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.servicebrokers.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceBrokersService extends Common {
    public CreateServiceBrokerResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokers().create(CreateServiceBrokerRequest.builder().build()).block();
    }

    public Void delete(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokers().delete(DeleteServiceBrokerRequest.builder().build()).block();
    }

    public GetServiceBrokerResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokers().get(GetServiceBrokerRequest.builder().build()).block();
    }

    public ListServiceBrokersResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokers().list(ListServiceBrokersRequest.builder().build()).block();
    }

    public UpdateServiceBrokerResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceBrokers().update(UpdateServiceBrokerRequest.builder().build()).block();
    }

}
