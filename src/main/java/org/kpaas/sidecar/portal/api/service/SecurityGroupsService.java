package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.securitygroups.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class SecurityGroupsService extends Common {
    public AssociateSecurityGroupSpaceResponse associateSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().associateSpace(AssociateSecurityGroupSpaceRequest.builder().build()).block();
    }

    public CreateSecurityGroupResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().create(CreateSecurityGroupRequest.builder().build()).block();
    }

    public DeleteSecurityGroupResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().delete(DeleteSecurityGroupRequest.builder().build()).block();
    }

    public GetSecurityGroupResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().get(GetSecurityGroupRequest.builder().build()).block();
    }

    public ListSecurityGroupsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().list(ListSecurityGroupsRequest.builder().build()).block();
    }

    public ListSecurityGroupRunningDefaultsResponse listRunningDefaults(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().listRunningDefaults(ListSecurityGroupRunningDefaultsRequest.builder().build()).block();
    }

    public ListSecurityGroupSpacesResponse listSpaces(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().listSpaces(ListSecurityGroupSpacesRequest.builder().build()).block();
    }

    public ListSecurityGroupStagingDefaultsResponse listStagingDefaults(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().listStagingDefaults(ListSecurityGroupStagingDefaultsRequest.builder().build()).block();
    }

    public Void removeRunningDefault(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().removeRunningDefault(RemoveSecurityGroupRunningDefaultRequest.builder().build()).block();
    }

    public Void removeSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().removeSpace(RemoveSecurityGroupSpaceRequest.builder().build()).block();
    }

    public Void removeStagingDefault(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().removeStagingDefault(RemoveSecurityGroupStagingDefaultRequest.builder().build()).block();
    }

    public SetSecurityGroupRunningDefaultResponse setRunningDefault(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().setRunningDefault(SetSecurityGroupRunningDefaultRequest.builder().build()).block();
    }

    public SetSecurityGroupStagingDefaultResponse setStagingDefault(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().setStagingDefault(SetSecurityGroupStagingDefaultRequest.builder().build()).block();
    }

    public UpdateSecurityGroupResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).securityGroups().update(UpdateSecurityGroupRequest.builder().build()).block();
    }
}
