package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.buildpacks.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BuildpacksServiceV3 extends Common {
    public ListBuildpacksResponse list() {
        return cloudFoundryClient(tokenProvider()).buildpacksV3().list(ListBuildpacksRequest.builder().build()).block();
    }

    public CreateBuildpackResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacksV3().create(CreateBuildpackRequest.builder().name("create-buildpack").build()).block();
    }
    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacksV3().delete(DeleteBuildpackRequest.builder().buildpackId(guid).build()).block();
    }
    public GetBuildpackResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacksV3().get(GetBuildpackRequest.builder().buildpackId(guid).build()).block();
    }

    public UpdateBuildpackResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).buildpacksV3().update(UpdateBuildpackRequest.builder().buildpackId(guid).build()).block();
    }
    public UploadBuildpackResponse upload(String guid) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("buildpack", ".zip");

            return cloudFoundryClient(tokenProvider()).buildpacksV3()
                    .upload(UploadBuildpackRequest.builder()
                            .buildpackId(guid)
                            .bits(tempFile.toPath())
                            .build())
                    .block();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary file for buildpack upload", e);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }
}
