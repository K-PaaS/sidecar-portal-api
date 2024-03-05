package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.domains.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class DomainsService extends Common {
    public CreateDomainResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domains().create(CreateDomainRequest.builder().build()).block();
    }

    public DeleteDomainResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domains().delete(DeleteDomainRequest.builder().build()).block();
    }

    public GetDomainResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domains().get(GetDomainRequest.builder().build()).block();
    }

    public ListDomainsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domains().list(ListDomainsRequest.builder().build()).block();
    }

    public ListDomainSpacesResponse listSpaces(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domains().listSpaces(ListDomainSpacesRequest.builder().build()).block();
    }
}
