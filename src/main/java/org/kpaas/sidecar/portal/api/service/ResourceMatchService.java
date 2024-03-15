package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.resourcematch.ListMatchingResourcesRequest;
import org.cloudfoundry.client.v2.resourcematch.ListMatchingResourcesResponse;
import org.kpaas.sidecar.portal.api.common.Common;

public class ResourceMatchService extends Common {
    public ListMatchingResourcesResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).resourceMatch().list(ListMatchingResourcesRequest.builder().build()).block();
    }
}
