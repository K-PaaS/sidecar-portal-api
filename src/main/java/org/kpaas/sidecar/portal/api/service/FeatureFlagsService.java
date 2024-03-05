package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.featureflags.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class FeatureFlagsService extends Common {
    public GetFeatureFlagResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).featureFlags().get(GetFeatureFlagRequest.builder().build()).block();
    }

    public ListFeatureFlagsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).featureFlags().list(ListFeatureFlagsRequest.builder().build()).block();
    }

    public SetFeatureFlagResponse set(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).featureFlags().set(SetFeatureFlagRequest.builder().build()).block();
    }
}
