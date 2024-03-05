package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.privatedomains.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class PrivateDomainsService extends Common {
    public CreatePrivateDomainResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).privateDomains().create(CreatePrivateDomainRequest.builder().build()).block();
    }

    public DeletePrivateDomainResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).privateDomains().delete(DeletePrivateDomainRequest.builder().build()).block();
    }

    public GetPrivateDomainResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).privateDomains().get(GetPrivateDomainRequest.builder().build()).block();
    }
    public ListPrivateDomainsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).privateDomains().list(ListPrivateDomainsRequest.builder().build()).block();
    }

    public ListPrivateDomainSharedOrganizationsResponse listSharedOrganizations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).privateDomains().listSharedOrganizations(ListPrivateDomainSharedOrganizationsRequest.builder().build()).block();
    }

}
