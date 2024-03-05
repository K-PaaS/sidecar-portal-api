package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.buildpacks.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class BuildpacksServiceV3 extends Common {
    public ListBuildpacksResponse list(String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacksV3().list(ListBuildpacksRequest.builder().build()).block();
    }

    public CreateBuildpackResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacksV3().create(CreateBuildpackRequest.builder().build()).block();
    }
    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacksV3().delete(DeleteBuildpackRequest.builder().build()).block();
    }
    public GetBuildpackResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacksV3().get(GetBuildpackRequest.builder().build()).block();
    }

    public UpdateBuildpackResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).buildpacksV3().update(UpdateBuildpackRequest.builder().build()).block();
    }
    public UploadBuildpackResponse upload(String guid, String token) {
        return   cloudFoundryClient(tokenProvider(token)).buildpacksV3().upload(UploadBuildpackRequest.builder().build()).block();
    }
}
