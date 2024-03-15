package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.servicekeys.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceKeysService extends Common {
    public CreateServiceKeyResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceKeys().create(CreateServiceKeyRequest.builder().build()).block();
    }

    public Void delete(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceKeys().delete(DeleteServiceKeyRequest.builder().build()).block();
    }

    public GetServiceKeyResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceKeys().get(GetServiceKeyRequest.builder().build()).block();
    }

    public ListServiceKeysResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceKeys().list(ListServiceKeysRequest.builder().build()).block();
    }
}
