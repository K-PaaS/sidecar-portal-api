package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.domains.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Domain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainsServiceV3 extends Common {
    public CheckReservedRoutesResponse checkReservedRoutes(String guid) {
        return cloudFoundryClient(tokenProvider()).domainsV3().checkReservedRoutes(CheckReservedRoutesRequest.builder().build()).block();
    }

    public CreateDomainResponse create(Domain domain) {
        return cloudFoundryClient(tokenProvider())
                .domainsV3()
                .create(CreateDomainRequest
                        .builder()
                        .name(domain.getName())
                        .build())
                .block();
    }

    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider())
                .domainsV3()
                .delete(DeleteDomainRequest
                        .builder()
                        .domainId(guid)
                        .build())
                .block();
    }

    public GetDomainResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .domainsV3()
                .get(GetDomainRequest
                        .builder()
                        .domainId(guid)
                        .build())
                .block();
    }

    public ListDomainsResponse list(List<String> names, List<String> owningOrgGuids) {
        names = stringListNullCheck(names);
        owningOrgGuids = stringListNullCheck(owningOrgGuids);

        return cloudFoundryClient(tokenProvider())
                .domainsV3()
                .list(ListDomainsRequest
                        .builder()
                        .names(names)
                        .owningOrganizationIds(owningOrgGuids)
                        .build())
                .block();
    }

    public ShareDomainResponse share(String guid) {
        return cloudFoundryClient(tokenProvider()).domainsV3().share(ShareDomainRequest.builder().build()).block();
    }

    public Void unshare(String guid) {
        return cloudFoundryClient(tokenProvider()).domainsV3().unshare(UnshareDomainRequest.builder().build()).block();
    }

    public UpdateDomainResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).domainsV3().update(UpdateDomainRequest.builder().build()).block();
    }
}
