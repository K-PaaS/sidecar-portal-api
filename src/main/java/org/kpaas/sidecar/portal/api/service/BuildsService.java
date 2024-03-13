package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.builds.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class BuildsService extends Common {
    public CreateBuildResponse create(String packageGuid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .builds()
                .create(CreateBuildRequest
                        .builder()
                        .getPackage(Relationship.builder().id(packageGuid).build())
                        //.lifecycle(Lifecycle.builder().type(LifecycleType.KPACK).build())
                        .build())
                .block();
    }

    public GetBuildResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .builds()
                .get(GetBuildRequest
                        .builder()
                        .buildId(guid)
                        .build())
                .block();
    }

    public ListBuildsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).builds().list(ListBuildsRequest.builder().build()).block();
    }
}
