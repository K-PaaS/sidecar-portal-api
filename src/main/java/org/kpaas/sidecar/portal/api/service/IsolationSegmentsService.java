package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.isolationsegments.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.IsolationSegment;
import org.springframework.stereotype.Service;

@Service
public class IsolationSegmentsService extends Common {
    public AddIsolationSegmentOrganizationEntitlementResponse addOrganizationEntitlement(String guid) {
        return cloudFoundryClient(tokenProvider()).isolationSegments().addOrganizationEntitlement(AddIsolationSegmentOrganizationEntitlementRequest.builder().isolationSegmentId(guid).build()).block();
    }

    public CreateIsolationSegmentResponse create(IsolationSegment isolationSegment) {
        return cloudFoundryClient(tokenProvider())
                .isolationSegments()
                .create(CreateIsolationSegmentRequest
                        .builder()
                        .name(isolationSegment.getName())
                        .build())
                .block();
    }

    public Void delete(String guid) {
        return cloudFoundryClient(tokenProvider()).isolationSegments().delete(DeleteIsolationSegmentRequest.builder().isolationSegmentId(guid).build()).block();
    }

    public GetIsolationSegmentResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).isolationSegments().get(GetIsolationSegmentRequest.builder().isolationSegmentId(guid).build()).block();
    }

    public ListIsolationSegmentsResponse list() {
        return cloudFoundryClient(tokenProvider())
                .isolationSegments()
                .list(ListIsolationSegmentsRequest
                        .builder()
                        .build())
                .block();
    }

    public ListIsolationSegmentEntitledOrganizationsResponse listEntitledOrganizations(String guid) {
        return cloudFoundryClient(tokenProvider()).isolationSegments().listEntitledOrganizations(ListIsolationSegmentEntitledOrganizationsRequest.builder().isolationSegmentId(guid).build()).block();
    }

    public ListIsolationSegmentOrganizationsRelationshipResponse listOrganizationsRelationship(String guid) {
        return cloudFoundryClient(tokenProvider()).isolationSegments().listOrganizationsRelationship(ListIsolationSegmentOrganizationsRelationshipRequest.builder().isolationSegmentId(guid).build()).block();
    }

    public ListIsolationSegmentSpacesRelationshipResponse listSpacesRelationship(String guid) {
        return cloudFoundryClient(tokenProvider()).isolationSegments().listSpacesRelationship(ListIsolationSegmentSpacesRelationshipRequest.builder().isolationSegmentId(guid).build()).block();
    }

    public Void removeOrganizationEntitlement(String guid) {
        return cloudFoundryClient(tokenProvider())
                .isolationSegments()
                .removeOrganizationEntitlement(RemoveIsolationSegmentOrganizationEntitlementRequest.builder()
                        .isolationSegmentId(guid)
                        .organizationId(guid)
                        .build())
                .block();
    }

    public UpdateIsolationSegmentResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).isolationSegments().update(UpdateIsolationSegmentRequest.builder().isolationSegmentId(guid)
                .name("update").build()).block();
    }
}
