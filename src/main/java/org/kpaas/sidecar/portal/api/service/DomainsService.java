package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.domains.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class DomainsService extends Common {
    public CreateDomainResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).domains().create(CreateDomainRequest.builder().build()).block();
    }

    public DeleteDomainResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).domains().delete(DeleteDomainRequest.builder().build()).block();
    }

    public GetDomainResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).domains().get(GetDomainRequest.builder().build()).block();
    }

    public ListDomainsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).domains().list(ListDomainsRequest.builder().build()).block();
    }

    public ListDomainSpacesResponse listSpaces(String guid) {
        return cloudFoundryClient(tokenProvider()).domains().listSpaces(ListDomainSpacesRequest.builder().build()).block();
    }
}
