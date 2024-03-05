package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Checksum;
import org.cloudfoundry.client.v3.ChecksumType;
import org.cloudfoundry.client.v3.resourcematch.ListMatchingResourcesRequest;
import org.cloudfoundry.client.v3.resourcematch.ListMatchingResourcesResponse;
import org.cloudfoundry.client.v3.resourcematch.MatchedResource;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class ResourceMatchServiceV3 extends Common {
    public ListMatchingResourcesResponse list(String token) {
        return cloudFoundryClient(tokenProvider(token))
                .resourceMatchV3()
                .list(ListMatchingResourcesRequest
                        .builder()
                        .resource(MatchedResource
                                .builder()
                                .checksum(Checksum.builder()
                                        .type(ChecksumType.SHA256)
                                        .value("test")
                                        .build())
                                .size(1)
                                .build())
                        .build())
                .block();
    }
}
