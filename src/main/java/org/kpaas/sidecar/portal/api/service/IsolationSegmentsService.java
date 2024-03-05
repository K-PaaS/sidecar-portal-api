package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.isolationsegments.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class IsolationSegmentsService extends Common {
    public AddIsolationSegmentOrganizationEntitlementResponse addOrganizationEntitlement(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().addOrganizationEntitlement(AddIsolationSegmentOrganizationEntitlementRequest.builder().build()).block();
    }

    public CreateIsolationSegmentResponse create(IsolationSegment isolationSegment, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .isolationSegments()
                .create(CreateIsolationSegmentRequest
                        .builder()
                        .name(isolationSegment.getName())
                        .build())
                .block();
    }

    public Void delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().delete(DeleteIsolationSegmentRequest.builder().build()).block();
    }

    public GetIsolationSegmentResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().get(GetIsolationSegmentRequest.builder().build()).block();
    }

    public ListIsolationSegmentsResponse list(String token) {
        return cloudFoundryClient(tokenProvider(token))
                .isolationSegments()
                .list(ListIsolationSegmentsRequest
                        .builder()
                        .build())
                .block();
    }

    public ListIsolationSegmentEntitledOrganizationsResponse listEntitledOrganizations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().listEntitledOrganizations(ListIsolationSegmentEntitledOrganizationsRequest.builder().build()).block();
    }

    public ListIsolationSegmentOrganizationsRelationshipResponse listOrganizationsRelationship(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().listOrganizationsRelationship(ListIsolationSegmentOrganizationsRelationshipRequest.builder().build()).block();
    }

    public ListIsolationSegmentSpacesRelationshipResponse listSpacesRelationship(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().listSpacesRelationship(ListIsolationSegmentSpacesRelationshipRequest.builder().build()).block();
    }

    public String removeOrganizationEntitlement(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().removeOrganizationEntitlement(RemoveIsolationSegmentOrganizationEntitlementRequest.builder().build()).block().toString();
    }

    public UpdateIsolationSegmentResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).isolationSegments().update(UpdateIsolationSegmentRequest.builder().build()).block();
    }
}
