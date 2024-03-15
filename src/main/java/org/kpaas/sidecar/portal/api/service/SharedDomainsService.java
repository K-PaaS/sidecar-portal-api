package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.shareddomains.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class SharedDomainsService extends Common {
    public CreateSharedDomainResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).sharedDomains().create(CreateSharedDomainRequest.builder().build()).block();
    }

    public DeleteSharedDomainResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).sharedDomains().delete(DeleteSharedDomainRequest.builder().build()).block();
    }

    public GetSharedDomainResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).sharedDomains().get(GetSharedDomainRequest.builder().build()).block();
    }

    public ListSharedDomainsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).sharedDomains().list(ListSharedDomainsRequest.builder().build()).block();
    }
}
