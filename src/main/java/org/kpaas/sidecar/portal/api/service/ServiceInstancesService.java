package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.serviceinstances.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceInstancesService extends Common {
    public CreateServiceInstanceResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().create(CreateServiceInstanceRequest.builder().build()).block();
    }

    public DeleteServiceInstanceResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().delete(DeleteServiceInstanceRequest.builder().build()).block();
    }

    public GetServiceInstanceResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().get(GetServiceInstanceRequest.builder().build()).block();
    }

    public GetServiceInstanceParametersResponse getParameters(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().getParameters(GetServiceInstanceParametersRequest.builder().build()).block();
    }

    public GetServiceInstancePermissionsResponse getPermissions(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().getPermissions(GetServiceInstancePermissionsRequest.builder().build()).block();
    }

    public ListServiceInstancesResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().list(ListServiceInstancesRequest.builder().build()).block();
    }

    public ListServiceInstanceRoutesResponse listRoutes(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().listRoutes(ListServiceInstanceRoutesRequest.builder().build()).block();
    }

    public ListServiceInstanceServiceBindingsResponse listServiceBindings(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().listServiceBindings(ListServiceInstanceServiceBindingsRequest.builder().build()).block();
    }

    public ListServiceInstanceServiceKeysResponse listServiceKeys(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().listServiceKeys(ListServiceInstanceServiceKeysRequest.builder().build()).block();
    }

    public Void unbindRoute(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().unbindRoute(UnbindServiceInstanceRouteRequest.builder().build()).block();
    }

    public UpdateServiceInstanceResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstances().update(UpdateServiceInstanceRequest.builder().build()).block();
    }
}
