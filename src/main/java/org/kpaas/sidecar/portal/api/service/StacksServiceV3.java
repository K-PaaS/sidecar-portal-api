package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.stacks.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class StacksServiceV3 extends Common {
    public CreateStackResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacksV3().create(CreateStackRequest.builder().build()).block();
    }

    public Void delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacksV3().delete(DeleteStackRequest.builder().build()).block();
    }

    public GetStackResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacksV3().get(GetStackRequest.builder().build()).block();
    }

    public ListStacksResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacksV3().list(ListStacksRequest.builder().build()).block();
    }

    public UpdateStackResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacksV3().update(UpdateStackRequest.builder().build()).block();
    }
}
