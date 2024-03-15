package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.droplets.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class DropletsService extends Common {
    public CopyDropletResponse copy(String guid) {
        return cloudFoundryClient(tokenProvider()).droplets().copy(CopyDropletRequest.builder().build()).block();
    }

    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider()).droplets().delete(DeleteDropletRequest.builder().build()).block();
    }

    public GetDropletResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .droplets()
                .get(GetDropletRequest
                        .builder()
                        .dropletId(guid)
                        .build())
                .block();
    }

    public ListDropletsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).droplets().list(ListDropletsRequest.builder().build()).block();
    }
}
