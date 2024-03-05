package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.domains.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Domain;
import org.springframework.stereotype.Service;

@Service
public class DomainsServiceV3 extends Common {
    public CheckReservedRoutesResponse checkReservedRoutes(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domainsV3().checkReservedRoutes(CheckReservedRoutesRequest.builder().build()).block();
    }

    public CreateDomainResponse create(Domain domain, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .domainsV3()
                .create(CreateDomainRequest
                        .builder()
                        .name(domain.getName())
                        .build())
                .block();
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .domainsV3()
                .delete(DeleteDomainRequest
                        .builder()
                        .domainId(guid)
                        .build())
                .block();
    }

    public GetDomainResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .domainsV3()
                .get(GetDomainRequest
                        .builder()
                        .domainId(guid)
                        .build())
                .block();
    }

    public ListDomainsResponse list(String token) {
        return cloudFoundryClient(tokenProvider(token))
                .domainsV3()
                .list(ListDomainsRequest
                        .builder()
                        .build())
                .block();
    }

    public ShareDomainResponse share(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domainsV3().share(ShareDomainRequest.builder().build()).block();
    }

    public Void unshare(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domainsV3().unshare(UnshareDomainRequest.builder().build()).block();
    }

    public UpdateDomainResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).domainsV3().update(UpdateDomainRequest.builder().build()).block();
    }
}
