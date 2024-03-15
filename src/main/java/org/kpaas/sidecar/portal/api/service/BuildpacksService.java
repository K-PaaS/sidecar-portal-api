package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.buildpacks.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class BuildpacksService extends Common {
    public CreateBuildpackResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacks().create(CreateBuildpackRequest.builder().build()).block();
    }

    public DeleteBuildpackResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacks().delete(DeleteBuildpackRequest.builder().build()).block();
    }

    public GetBuildpackResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacks().get(GetBuildpackRequest.builder().build()).block();
    }

    public ListBuildpacksResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacks().list(ListBuildpacksRequest.builder().build()).block();
    }

    public UpdateBuildpackResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacks().update(UpdateBuildpackRequest.builder().build()).block();
    }

    public UploadBuildpackResponse upload(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacks().upload(UploadBuildpackRequest.builder().build()).block();
    }
}
