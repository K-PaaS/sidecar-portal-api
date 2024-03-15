package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.securitygroups.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class SecurityGroupsService extends Common {
    public AssociateSecurityGroupSpaceResponse associateSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().associateSpace(AssociateSecurityGroupSpaceRequest.builder().build()).block();
    }

    public CreateSecurityGroupResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().create(CreateSecurityGroupRequest.builder().build()).block();
    }

    public DeleteSecurityGroupResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().delete(DeleteSecurityGroupRequest.builder().build()).block();
    }

    public GetSecurityGroupResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().get(GetSecurityGroupRequest.builder().build()).block();
    }

    public ListSecurityGroupsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().list(ListSecurityGroupsRequest.builder().build()).block();
    }

    public ListSecurityGroupRunningDefaultsResponse listRunningDefaults(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().listRunningDefaults(ListSecurityGroupRunningDefaultsRequest.builder().build()).block();
    }

    public ListSecurityGroupSpacesResponse listSpaces(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().listSpaces(ListSecurityGroupSpacesRequest.builder().build()).block();
    }

    public ListSecurityGroupStagingDefaultsResponse listStagingDefaults(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().listStagingDefaults(ListSecurityGroupStagingDefaultsRequest.builder().build()).block();
    }

    public Void removeRunningDefault(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().removeRunningDefault(RemoveSecurityGroupRunningDefaultRequest.builder().build()).block();
    }

    public Void removeSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().removeSpace(RemoveSecurityGroupSpaceRequest.builder().build()).block();
    }

    public Void removeStagingDefault(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().removeStagingDefault(RemoveSecurityGroupStagingDefaultRequest.builder().build()).block();
    }

    public SetSecurityGroupRunningDefaultResponse setRunningDefault(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().setRunningDefault(SetSecurityGroupRunningDefaultRequest.builder().build()).block();
    }

    public SetSecurityGroupStagingDefaultResponse setStagingDefault(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().setStagingDefault(SetSecurityGroupStagingDefaultRequest.builder().build()).block();
    }

    public UpdateSecurityGroupResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).securityGroups().update(UpdateSecurityGroupRequest.builder().build()).block();
    }
}
