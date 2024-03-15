package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.spaces.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Space;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpacesServiceV3 extends Common {
    public ApplyManifestResponse applyManifest(String guid) {

        return cloudFoundryClient(tokenProvider())
                .spacesV3()
                .applyManifest(ApplyManifestRequest
                        .builder()
                        .manifest()
                        .spaceId(guid)
                        .build())
                .block();
    }

    public AssignSpaceIsolationSegmentResponse assignIsolationSegment(String guid) {
        return cloudFoundryClient(tokenProvider()).spacesV3().assignIsolationSegment(AssignSpaceIsolationSegmentRequest.builder().build()).block();
    }

    public CreateSpaceResponse create(Space space) {
        return cloudFoundryClient(tokenProvider())
                .spacesV3()
                .create(CreateSpaceRequest
                        .builder()
                        .relationships(space.getRelationships())
                        .name(space.getName())
                        .build())
                .block();
    }

    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider())
                .spacesV3()
                .delete(DeleteSpaceRequest
                        .builder()
                        .spaceId(guid)
                        .build())
                .block();
    }

    public String deleteUnmappedRoutes(String guid) {
        return cloudFoundryClient(tokenProvider()).spacesV3().deleteUnmappedRoutes(DeleteUnmappedRoutesRequest.builder().build()).block();
    }

    public GetSpaceResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .spacesV3()
                .get(GetSpaceRequest
                        .builder()
                        .spaceId(guid)
                        .build())
                .block();
    }

    public GetSpaceIsolationSegmentResponse getIsolationSegment(String guid) {
        return cloudFoundryClient(tokenProvider()).spacesV3().getIsolationSegment(GetSpaceIsolationSegmentRequest.builder().build()).block();
    }

    public ListSpacesResponse list(List<String> orgGuids, List<String> names) {
        orgGuids = stringListNullCheck(orgGuids);
        names = stringListNullCheck(names);

        return cloudFoundryClient(tokenProvider())
                .spacesV3()
                .list(ListSpacesRequest
                        .builder()
                        .organizationIds(orgGuids)
                        .names(names)
                        .build())
                .block();
    }

    public UpdateSpaceResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).spacesV3().update(UpdateSpaceRequest.builder().build()).block();
    }
}
