package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.privatedomains.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class PrivateDomainsService extends Common {
    public CreatePrivateDomainResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).privateDomains().create(CreatePrivateDomainRequest.builder().build()).block();
    }

    public DeletePrivateDomainResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).privateDomains().delete(DeletePrivateDomainRequest.builder().build()).block();
    }

    public GetPrivateDomainResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).privateDomains().get(GetPrivateDomainRequest.builder().build()).block();
    }
    public ListPrivateDomainsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).privateDomains().list(ListPrivateDomainsRequest.builder().build()).block();
    }

    public ListPrivateDomainSharedOrganizationsResponse listSharedOrganizations(String guid) {
        return cloudFoundryClient(tokenProvider()).privateDomains().listSharedOrganizations(ListPrivateDomainSharedOrganizationsRequest.builder().build()).block();
    }

}
