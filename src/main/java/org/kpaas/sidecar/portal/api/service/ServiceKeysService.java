package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.servicekeys.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceKeysService extends Common {
    public CreateServiceKeyResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceKeys().create(CreateServiceKeyRequest.builder().build()).block();
    }

    public Void delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceKeys().delete(DeleteServiceKeyRequest.builder().build()).block();
    }

    public GetServiceKeyResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceKeys().get(GetServiceKeyRequest.builder().build()).block();
    }

    public ListServiceKeysResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceKeys().list(ListServiceKeysRequest.builder().build()).block();
    }
}
