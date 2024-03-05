package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.spaces.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class SpacesService extends Common {
    public AssociateSpaceAuditorResponse associateAuditor(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().associateAuditor(AssociateSpaceAuditorRequest.builder().build()).block();
    }

    public AssociateSpaceAuditorByUsernameResponse associateAuditorByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().associateAuditorByUsername(AssociateSpaceAuditorByUsernameRequest.builder().build()).block();
    }

    public AssociateSpaceDeveloperResponse associateDeveloper(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().associateDeveloper(AssociateSpaceDeveloperRequest.builder().build()).block();
    }

    public AssociateSpaceDeveloperByUsernameResponse associateDeveloperByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().associateDeveloperByUsername(AssociateSpaceDeveloperByUsernameRequest.builder().build()).block();
    }

    public AssociateSpaceManagerResponse associateManager(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().associateManager(AssociateSpaceManagerRequest.builder().build()).block();
    }

    public AssociateSpaceManagerByUsernameResponse associateManagerByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().associateManagerByUsername(AssociateSpaceManagerByUsernameRequest.builder().build()).block();
    }

    public AssociateSpaceSecurityGroupResponse associateSecurityGroup(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().associateSecurityGroup(AssociateSpaceSecurityGroupRequest.builder().build()).block();
    }

    public CreateSpaceResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().create(CreateSpaceRequest.builder().build()).block();
    }

    public DeleteSpaceResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().delete(DeleteSpaceRequest.builder().build()).block();
    }

    public GetSpaceResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().get(GetSpaceRequest.builder().build()).block();
    }

    public GetSpaceSummaryResponse getSummary(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().getSummary(GetSpaceSummaryRequest.builder().build()).block();
    }

    public ListSpacesResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().list(ListSpacesRequest.builder().build()).block();
    }

    public ListSpaceApplicationsResponse listApplications(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listApplications(ListSpaceApplicationsRequest.builder().build()).block();
    }

    public ListSpaceAuditorsResponse listAuditors(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listAuditors(ListSpaceAuditorsRequest.builder().build()).block();
    }

    public ListSpaceDevelopersResponse listDevelopers(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listDevelopers(ListSpaceDevelopersRequest.builder().build()).block();
    }

    public ListSpaceDomainsResponse listDomains(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listDomains(ListSpaceDomainsRequest.builder().build()).block();
    }

    public ListSpaceEventsResponse listEvents(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listEvents(ListSpaceEventsRequest.builder().build()).block();
    }

    public ListSpaceManagersResponse listManagers(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listManagers(ListSpaceManagersRequest.builder().build()).block();
    }

    public ListSpaceRoutesResponse listRoutes(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listRoutes(ListSpaceRoutesRequest.builder().build()).block();
    }

    public ListSpaceSecurityGroupsResponse listSecurityGroups(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listSecurityGroups(ListSpaceSecurityGroupsRequest.builder().build()).block();
    }

    public ListSpaceServiceInstancesResponse listServiceInstances(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listServiceInstances(ListSpaceServiceInstancesRequest.builder().build()).block();
    }

    public ListSpaceServicesResponse listServices(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listServices(ListSpaceServicesRequest.builder().build()).block();
    }

    public ListSpaceUserRolesResponse listUserRoles(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaces().listUserRoles(ListSpaceUserRolesRequest.builder().build()).block();
    }
}
