package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.shareddomains.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class SharedDomainsService extends Common {
    public CreateSharedDomainResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).sharedDomains().create(CreateSharedDomainRequest.builder().build()).block();
    }

    public DeleteSharedDomainResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).sharedDomains().delete(DeleteSharedDomainRequest.builder().build()).block();
    }

    public GetSharedDomainResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).sharedDomains().get(GetSharedDomainRequest.builder().build()).block();
    }

    public ListSharedDomainsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).sharedDomains().list(ListSharedDomainsRequest.builder().build()).block();
    }
}
