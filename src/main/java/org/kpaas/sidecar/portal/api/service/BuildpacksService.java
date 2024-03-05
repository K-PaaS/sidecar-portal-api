package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.buildpacks.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class BuildpacksService extends Common {
    public CreateBuildpackResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacks().create(CreateBuildpackRequest.builder().build()).block();
    }

    public DeleteBuildpackResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacks().delete(DeleteBuildpackRequest.builder().build()).block();
    }

    public GetBuildpackResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacks().get(GetBuildpackRequest.builder().build()).block();
    }

    public ListBuildpacksResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacks().list(ListBuildpacksRequest.builder().build()).block();
    }

    public UpdateBuildpackResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacks().update(UpdateBuildpackRequest.builder().build()).block();
    }

    public UploadBuildpackResponse upload(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacks().upload(UploadBuildpackRequest.builder().build()).block();
    }
}
