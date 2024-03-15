package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.serviceinstances.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ServiceInstancesService extends Common {
    public CreateServiceInstanceResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().create(CreateServiceInstanceRequest.builder().build()).block();
    }

    public DeleteServiceInstanceResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().delete(DeleteServiceInstanceRequest.builder().build()).block();
    }

    public GetServiceInstanceResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().get(GetServiceInstanceRequest.builder().build()).block();
    }

    public GetServiceInstanceParametersResponse getParameters(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().getParameters(GetServiceInstanceParametersRequest.builder().build()).block();
    }

    public GetServiceInstancePermissionsResponse getPermissions(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().getPermissions(GetServiceInstancePermissionsRequest.builder().build()).block();
    }

    public ListServiceInstancesResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().list(ListServiceInstancesRequest.builder().build()).block();
    }

    public ListServiceInstanceRoutesResponse listRoutes(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().listRoutes(ListServiceInstanceRoutesRequest.builder().build()).block();
    }

    public ListServiceInstanceServiceBindingsResponse listServiceBindings(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().listServiceBindings(ListServiceInstanceServiceBindingsRequest.builder().build()).block();
    }

    public ListServiceInstanceServiceKeysResponse listServiceKeys(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().listServiceKeys(ListServiceInstanceServiceKeysRequest.builder().build()).block();
    }

    public Void unbindRoute(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().unbindRoute(UnbindServiceInstanceRouteRequest.builder().build()).block();
    }

    public UpdateServiceInstanceResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).serviceInstances().update(UpdateServiceInstanceRequest.builder().build()).block();
    }
}
