package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.spaces.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class SpacesService extends Common {
    public AssociateSpaceAuditorResponse associateAuditor(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().associateAuditor(AssociateSpaceAuditorRequest.builder().build()).block();
    }

    public AssociateSpaceAuditorByUsernameResponse associateAuditorByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().associateAuditorByUsername(AssociateSpaceAuditorByUsernameRequest.builder().build()).block();
    }

    public AssociateSpaceDeveloperResponse associateDeveloper(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().associateDeveloper(AssociateSpaceDeveloperRequest.builder().build()).block();
    }

    public AssociateSpaceDeveloperByUsernameResponse associateDeveloperByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().associateDeveloperByUsername(AssociateSpaceDeveloperByUsernameRequest.builder().build()).block();
    }

    public AssociateSpaceManagerResponse associateManager(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().associateManager(AssociateSpaceManagerRequest.builder().build()).block();
    }

    public AssociateSpaceManagerByUsernameResponse associateManagerByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().associateManagerByUsername(AssociateSpaceManagerByUsernameRequest.builder().build()).block();
    }

    public AssociateSpaceSecurityGroupResponse associateSecurityGroup(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().associateSecurityGroup(AssociateSpaceSecurityGroupRequest.builder().build()).block();
    }

    public CreateSpaceResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().create(CreateSpaceRequest.builder().build()).block();
    }

    public DeleteSpaceResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().delete(DeleteSpaceRequest.builder().build()).block();
    }

    public GetSpaceResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().get(GetSpaceRequest.builder().build()).block();
    }

    public GetSpaceSummaryResponse getSummary(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().getSummary(GetSpaceSummaryRequest.builder().build()).block();
    }

    public ListSpacesResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().list(ListSpacesRequest.builder().build()).block();
    }

    public ListSpaceApplicationsResponse listApplications(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listApplications(ListSpaceApplicationsRequest.builder().build()).block();
    }

    public ListSpaceAuditorsResponse listAuditors(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listAuditors(ListSpaceAuditorsRequest.builder().build()).block();
    }

    public ListSpaceDevelopersResponse listDevelopers(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listDevelopers(ListSpaceDevelopersRequest.builder().build()).block();
    }

    public ListSpaceDomainsResponse listDomains(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listDomains(ListSpaceDomainsRequest.builder().build()).block();
    }

    public ListSpaceEventsResponse listEvents(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listEvents(ListSpaceEventsRequest.builder().build()).block();
    }

    public ListSpaceManagersResponse listManagers(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listManagers(ListSpaceManagersRequest.builder().build()).block();
    }

    public ListSpaceRoutesResponse listRoutes(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listRoutes(ListSpaceRoutesRequest.builder().build()).block();
    }

    public ListSpaceSecurityGroupsResponse listSecurityGroups(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listSecurityGroups(ListSpaceSecurityGroupsRequest.builder().build()).block();
    }

    public ListSpaceServiceInstancesResponse listServiceInstances(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listServiceInstances(ListSpaceServiceInstancesRequest.builder().build()).block();
    }

    public ListSpaceServicesResponse listServices(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listServices(ListSpaceServicesRequest.builder().build()).block();
    }

    public ListSpaceUserRolesResponse listUserRoles(String guid) {
        return cloudFoundryClient(tokenProvider()).spaces().listUserRoles(ListSpaceUserRolesRequest.builder().build()).block();
    }
}
