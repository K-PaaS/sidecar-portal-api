package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.organizations.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Organization;
import org.springframework.stereotype.Service;

@Service
public class OrganizationsServiceV3 extends Common {
    public AssignOrganizationDefaultIsolationSegmentResponse assignDefaultIsolationSegment(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationsV3().assignDefaultIsolationSegment(AssignOrganizationDefaultIsolationSegmentRequest.builder().build()).block();
    }

    public CreateOrganizationResponse create(Organization org, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .organizationsV3()
                .create(CreateOrganizationRequest
                        .builder()
                        .name(org.getName())
                        .build())
                .block();
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .organizationsV3()
                .delete(DeleteOrganizationRequest
                        .builder()
                        .organizationId(guid)
                        .build())
                .block();
    }

    public GetOrganizationResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .organizationsV3()
                .get(GetOrganizationRequest
                        .builder()
                        .organizationId(guid)
                        .build())
                .block();
    }

    public GetOrganizationDefaultDomainResponse getDefaultDomain(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .organizationsV3()
                .getDefaultDomain(GetOrganizationDefaultDomainRequest
                        .builder()
                        .organizationId(guid)
                        .build())
                .block();
    }

    public GetOrganizationDefaultIsolationSegmentResponse getDeafultIsolationSegment(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationsV3().getDefaultIsolationSegment(GetOrganizationDefaultIsolationSegmentRequest.builder().build()).block();
    }

    public GetOrganizationUsageSummaryResponse getUsageSummary(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationsV3().getUsageSummary(GetOrganizationUsageSummaryRequest.builder().build()).block();
    }

    public ListOrganizationsResponse list(String token) {
        return cloudFoundryClient(tokenProvider(token))
                .organizationsV3()
                .list(ListOrganizationsRequest
                        .builder()
                        .build())
                .block();
    }

    public ListOrganizationDomainsResponse listDomains(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .organizationsV3()
                .listDomains(ListOrganizationDomainsRequest
                        .builder()
                        .organizationId(guid)
                        .build())
                .block();
    }

    public UpdateOrganizationResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationsV3().update(UpdateOrganizationRequest.builder().build()).block();
    }
}
