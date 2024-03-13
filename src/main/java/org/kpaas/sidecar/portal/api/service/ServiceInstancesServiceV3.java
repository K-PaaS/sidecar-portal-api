package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.serviceinstances.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceInstancesServiceV3 extends Common {
    public CreateServiceInstanceResponse create(ServiceInstance serviceInstance, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .serviceInstancesV3()
                .create(CreateServiceInstanceRequest
                        .builder()
                        .name(serviceInstance.getName())
                        .type(serviceInstance.getType())
                        .relationships(serviceInstance.getRelationships())
                        .build())
                .block();
    }

    public Optional<String> delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .serviceInstancesV3().
                delete(DeleteServiceInstanceRequest
                        .builder()
                        .serviceInstanceId(guid)
                        .build())
                .block();
    }

    public GetServiceInstanceResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstancesV3().get(GetServiceInstanceRequest.builder().build()).block();
    }

    public GetManagedServiceParametersResponse getManagedServiceParameters(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstancesV3().getManagedServiceParameters(GetManagedServiceParametersRequest.builder().build()).block();
    }

    public GetUserProvidedCredentialsResponse getUserProvidedCredentials(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstancesV3().getUserProvidedCredentials(GetUserProvidedCredentialsRequest.builder().build()).block();
    }

    public ListServiceInstancesResponse list(List<String> spaceGuid, List<String> serviceInstanceNames,String token) {
        spaceGuid = stringListNullCheck(spaceGuid);
        serviceInstanceNames = stringListNullCheck(serviceInstanceNames);

        return cloudFoundryClient(tokenProvider(token))
                .serviceInstancesV3()
                .list(ListServiceInstancesRequest
                        .builder()
                        .spaceIds(spaceGuid)
                        .serviceInstanceNames(serviceInstanceNames)
                        .build())
                .block();
    }

    public ListSharedSpacesRelationshipResponse listSharedSpacesRelationship(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstancesV3().listSharedSpacesRelationship(ListSharedSpacesRelationshipRequest.builder().build()).block();
    }

    public ShareServiceInstanceResponse share(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstancesV3().share(ShareServiceInstanceRequest.builder().build()).block();
    }

    public Void unshare(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstancesV3().unshare(UnshareServiceInstanceRequest.builder().build()).block();
    }

    public UpdateServiceInstanceResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceInstancesV3().update(UpdateServiceInstanceRequest.builder().build()).block();
    }
}
