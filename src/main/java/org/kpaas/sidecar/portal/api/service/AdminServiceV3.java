package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.admin.ClearBuildpackCacheRequest;
import org.kpaas.sidecar.portal.api.common.Common;

public class AdminServiceV3 extends Common {
    public String clearBuildpackCache(String guid) {
        return cloudFoundryClient(tokenProvider()).adminV3().clearBuildpackCache(ClearBuildpackCacheRequest.builder().build()).block();
    }
}
