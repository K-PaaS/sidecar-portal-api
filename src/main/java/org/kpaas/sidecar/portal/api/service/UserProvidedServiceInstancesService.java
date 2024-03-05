package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.userprovidedserviceinstances.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class UserProvidedServiceInstancesService extends Common {
    public AssociateUserProvidedServiceInstanceRouteResponse associateRoute(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().associateRoute(AssociateUserProvidedServiceInstanceRouteRequest.builder().build()).block();
    }

    public CreateUserProvidedServiceInstanceResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().create(CreateUserProvidedServiceInstanceRequest.builder().build()).block();
    }

    public Void delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().delete(DeleteUserProvidedServiceInstanceRequest.builder().build()).block();
    }

    public GetUserProvidedServiceInstanceResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().get(GetUserProvidedServiceInstanceRequest.builder().build()).block();
    }

    public ListUserProvidedServiceInstancesResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().list(ListUserProvidedServiceInstancesRequest.builder().build()).block();
    }

    public ListUserProvidedServiceInstanceRoutesResponse listRoutes(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().listRoutes(ListUserProvidedServiceInstanceRoutesRequest.builder().build()).block();
    }

    public ListUserProvidedServiceInstanceServiceBindingsResponse listServiceBindings(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().listServiceBindings(ListUserProvidedServiceInstanceServiceBindingsRequest.builder().build()).block();
    }

    public Void removeRoute(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().removeRoute(RemoveUserProvidedServiceInstanceRouteRequest.builder().build()).block();
    }

    public UpdateUserProvidedServiceInstanceResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).userProvidedServiceInstances().update(UpdateUserProvidedServiceInstanceRequest.builder().build()).block();
    }
}
