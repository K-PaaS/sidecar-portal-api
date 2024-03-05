package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.stacks.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class StacksService extends Common {
    public CreateStackResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacks().create(CreateStackRequest.builder().build()).block();
    }

    public DeleteStackResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacks().delete(DeleteStackRequest.builder().build()).block();
    }

    public GetStackResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacks().get(GetStackRequest.builder().build()).block();
    }

    public ListStacksResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).stacks().list(ListStacksRequest.builder().build()).block();
    }
}
