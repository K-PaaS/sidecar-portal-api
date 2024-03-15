package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.featureflags.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class FeatureFlagsService extends Common {
    public GetFeatureFlagResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).featureFlags().get(GetFeatureFlagRequest.builder().build()).block();
    }

    public ListFeatureFlagsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).featureFlags().list(ListFeatureFlagsRequest.builder().build()).block();
    }

    public SetFeatureFlagResponse set(String guid) {
        return cloudFoundryClient(tokenProvider()).featureFlags().set(SetFeatureFlagRequest.builder().build()).block();
    }
}
