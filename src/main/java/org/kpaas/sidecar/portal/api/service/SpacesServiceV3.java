package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.spaces.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Space;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpacesServiceV3 extends Common {
    public ApplyManifestResponse applyManifest(String guid, String token) {

        return cloudFoundryClient(tokenProvider(token))
                .spacesV3()
                .applyManifest(ApplyManifestRequest
                        .builder()
                        .manifest()
                        .spaceId(guid)
                        .build())
                .block();
    }

    public AssignSpaceIsolationSegmentResponse assignIsolationSegment(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spacesV3().assignIsolationSegment(AssignSpaceIsolationSegmentRequest.builder().build()).block();
    }

    public CreateSpaceResponse create(Space space, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .spacesV3()
                .create(CreateSpaceRequest
                        .builder()
                        .relationships(space.getRelationships())
                        .name(space.getName())
                        .build())
                .block();
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .spacesV3()
                .delete(DeleteSpaceRequest
                        .builder()
                        .spaceId(guid)
                        .build())
                .block();
    }

    public String deleteUnmappedRoutes(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spacesV3().deleteUnmappedRoutes(DeleteUnmappedRoutesRequest.builder().build()).block();
    }

    public GetSpaceResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .spacesV3()
                .get(GetSpaceRequest
                        .builder()
                        .spaceId(guid)
                        .build())
                .block();
    }

    public GetSpaceIsolationSegmentResponse getIsolationSegment(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spacesV3().getIsolationSegment(GetSpaceIsolationSegmentRequest.builder().build()).block();
    }

    public ListSpacesResponse list(List<String> orgGuids, List<String> names, String token) {
        orgGuids = stringListNullCheck(orgGuids);
        names = stringListNullCheck(names);

        return cloudFoundryClient(tokenProvider(token))
                .spacesV3()
                .list(ListSpacesRequest
                        .builder()
                        .organizationIds(orgGuids)
                        .names(names)
                        .build())
                .block();
    }

    public UpdateSpaceResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spacesV3().update(UpdateSpaceRequest.builder().build()).block();
    }
}
