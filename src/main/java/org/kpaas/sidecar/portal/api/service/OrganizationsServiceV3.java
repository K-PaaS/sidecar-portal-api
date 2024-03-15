package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.organizations.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationsServiceV3 extends Common {
    public AssignOrganizationDefaultIsolationSegmentResponse assignDefaultIsolationSegment(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationsV3().assignDefaultIsolationSegment(AssignOrganizationDefaultIsolationSegmentRequest.builder().build()).block();
    }

    public CreateOrganizationResponse create(Organization org) {
        return cloudFoundryClient(tokenProvider())
                .organizationsV3()
                .create(CreateOrganizationRequest
                        .builder()
                        .name(org.getName())
                        .build())
                .block();
    }

    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider())
                .organizationsV3()
                .delete(DeleteOrganizationRequest
                        .builder()
                        .organizationId(guid)
                        .build())
                .block();
    }

    public GetOrganizationResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .organizationsV3()
                .get(GetOrganizationRequest
                        .builder()
                        .organizationId(guid)
                        .build())
                .block();
    }

    public GetOrganizationDefaultDomainResponse getDefaultDomain(String guid) {
        return cloudFoundryClient(tokenProvider())
                .organizationsV3()
                .getDefaultDomain(GetOrganizationDefaultDomainRequest
                        .builder()
                        .organizationId(guid)
                        .build())
                .block();
    }

    public GetOrganizationDefaultIsolationSegmentResponse getDeafultIsolationSegment(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationsV3().getDefaultIsolationSegment(GetOrganizationDefaultIsolationSegmentRequest.builder().build()).block();
    }

    public GetOrganizationUsageSummaryResponse getUsageSummary(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationsV3().getUsageSummary(GetOrganizationUsageSummaryRequest.builder().build()).block();
    }

    public ListOrganizationsResponse list(List<String> names) {
        names = stringListNullCheck(names);

        return cloudFoundryClient(tokenProvider())
                .organizationsV3()
                .list(ListOrganizationsRequest
                        .builder()
                        .names(names)
                        .build())
                .block();
    }

    public ListOrganizationDomainsResponse listDomains(String orgGuid, List<String> domainGuids, List<String> names) {
        domainGuids = stringListNullCheck(domainGuids);
        names = stringListNullCheck(names);

        return cloudFoundryClient(tokenProvider())
                .organizationsV3()
                .listDomains(ListOrganizationDomainsRequest
                        .builder()
                        .organizationId(orgGuid)
                        .domainIds(domainGuids)
                        .names(names)
                        .build())
                .block();
    }

    public UpdateOrganizationResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationsV3().update(UpdateOrganizationRequest.builder().build()).block();
    }
}
