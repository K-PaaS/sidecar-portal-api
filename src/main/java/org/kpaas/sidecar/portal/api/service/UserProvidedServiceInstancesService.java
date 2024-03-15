package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.userprovidedserviceinstances.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class UserProvidedServiceInstancesService extends Common {
    public AssociateUserProvidedServiceInstanceRouteResponse associateRoute(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().associateRoute(AssociateUserProvidedServiceInstanceRouteRequest.builder().build()).block();
    }

    public CreateUserProvidedServiceInstanceResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().create(CreateUserProvidedServiceInstanceRequest.builder().build()).block();
    }

    public Void delete(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().delete(DeleteUserProvidedServiceInstanceRequest.builder().build()).block();
    }

    public GetUserProvidedServiceInstanceResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().get(GetUserProvidedServiceInstanceRequest.builder().build()).block();
    }

    public ListUserProvidedServiceInstancesResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().list(ListUserProvidedServiceInstancesRequest.builder().build()).block();
    }

    public ListUserProvidedServiceInstanceRoutesResponse listRoutes(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().listRoutes(ListUserProvidedServiceInstanceRoutesRequest.builder().build()).block();
    }

    public ListUserProvidedServiceInstanceServiceBindingsResponse listServiceBindings(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().listServiceBindings(ListUserProvidedServiceInstanceServiceBindingsRequest.builder().build()).block();
    }

    public Void removeRoute(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().removeRoute(RemoveUserProvidedServiceInstanceRouteRequest.builder().build()).block();
    }

    public UpdateUserProvidedServiceInstanceResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).userProvidedServiceInstances().update(UpdateUserProvidedServiceInstanceRequest.builder().build()).block();
    }
}
