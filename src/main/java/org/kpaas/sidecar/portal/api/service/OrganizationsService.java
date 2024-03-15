package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.organizations.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class OrganizationsService extends Common {
    public AssociateOrganizationAuditorResponse associateAuditor(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateAuditor(AssociateOrganizationAuditorRequest.builder().build()).block();
    }

    public AssociateOrganizationAuditorByUsernameResponse associateAuditorByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateAuditorByUsername(AssociateOrganizationAuditorByUsernameRequest.builder().build()).block();
    }

    public AssociateOrganizationBillingManagerResponse associateBillingManager(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateBillingManager(AssociateOrganizationBillingManagerRequest.builder().build()).block();
    }

    public AssociateOrganizationBillingManagerByUsernameResponse associateBillingManagerByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateBillingManagerByUsername(AssociateOrganizationBillingManagerByUsernameRequest.builder().build()).block();
    }

    public AssociateOrganizationManagerResponse associateManager(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateManager(AssociateOrganizationManagerRequest.builder().build()).block();
    }

    public AssociateOrganizationManagerByUsernameResponse associateManagerByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateManagerByUsername(AssociateOrganizationManagerByUsernameRequest.builder().build()).block();
    }

    public AssociateOrganizationPrivateDomainResponse associatePrivateDomain(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associatePrivateDomain(AssociateOrganizationPrivateDomainRequest.builder().build()).block();
    }

    public AssociateOrganizationUserResponse associateUser(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateUser(AssociateOrganizationUserRequest.builder().build()).block();
    }

    public AssociateOrganizationUserByUsernameResponse associateUserByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().associateUserByUsername(AssociateOrganizationUserByUsernameRequest.builder().build()).block();
    }

    public CreateOrganizationResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().create(CreateOrganizationRequest.builder().build()).block();
    }

    public DeleteOrganizationResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().delete(DeleteOrganizationRequest.builder().build()).block();
    }

    public GetOrganizationResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().get(GetOrganizationRequest.builder().build()).block();
    }

    public GetOrganizationInstanceUsageResponse getInstanceUsage(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().getInstanceUsage(GetOrganizationInstanceUsageRequest.builder().build()).block();
    }

    public GetOrganizationMemoryUsageResponse getMemoryUsage(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().getMemoryUsage(GetOrganizationMemoryUsageRequest.builder().build()).block();
    }

    public GetOrganizationUserRolesResponse getUserRoles(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().getUserRoles(GetOrganizationUserRolesRequest.builder().build()).block();
    }

    public ListOrganizationsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().list(ListOrganizationsRequest.builder().build()).block();
    }

    public ListOrganizationAuditorsResponse listAuditors(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listAuditors(ListOrganizationAuditorsRequest.builder().build()).block();
    }

    public ListOrganizationBillingManagersResponse listBillingManagers(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listBillingManagers(ListOrganizationBillingManagersRequest.builder().build()).block();
    }

    public ListOrganizationDomainsResponse listDomains(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listDomains(ListOrganizationDomainsRequest.builder().build()).block();
    }

    public ListOrganizationManagersResponse listManagers(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listManagers(ListOrganizationManagersRequest.builder().build()).block();
    }

    public ListOrganizationPrivateDomainsResponse listPrivateDomains(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listPrivateDomains(ListOrganizationPrivateDomainsRequest.builder().build()).block();
    }

    public ListOrganizationServicesResponse listServices(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listServices(ListOrganizationServicesRequest.builder().build()).block();
    }

    public ListOrganizationSpaceQuotaDefinitionsResponse listSpaceQuotaDefinitions(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listSpaceQuotaDefinitions(ListOrganizationSpaceQuotaDefinitionsRequest.builder().build()).block();
    }

    public ListOrganizationSpacesResponse listSpaces(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listSpaces(ListOrganizationSpacesRequest.builder().build()).block();
    }

    public ListOrganizationUsersResponse listUsers(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().listUsers(ListOrganizationUsersRequest.builder().build()).block();
    }

    public Void removeAuditor(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeAuditor(RemoveOrganizationAuditorRequest.builder().build()).block();
    }

    public Void removeAuditorByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeAuditorByUsername(RemoveOrganizationAuditorByUsernameRequest.builder().build()).block();
    }

    public Void removeBillingManager(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeBillingManager(RemoveOrganizationBillingManagerRequest.builder().build()).block();
    }

    public Void removeBillingManagerByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeBillingManagerByUsername(RemoveOrganizationBillingManagerByUsernameRequest.builder().build()).block();
    }

    public Void removeManager(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeManager(RemoveOrganizationManagerRequest.builder().build()).block();
    }

    public Void removeManagerByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeManagerByUsername(RemoveOrganizationManagerByUsernameRequest.builder().build()).block();
    }

    public Void removePrivateDomain(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removePrivateDomain(RemoveOrganizationPrivateDomainRequest.builder().build()).block();
    }

    public Void removeUser(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeUser(RemoveOrganizationUserRequest.builder().build()).block();
    }

    public Void removeUserByUsername(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().removeUserByUsername(RemoveOrganizationUserByUsernameRequest.builder().build()).block();
    }

    public SummaryOrganizationResponse summary(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().summary(SummaryOrganizationRequest.builder().build()).block();
    }

    public UpdateOrganizationResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).organizations().update(UpdateOrganizationRequest.builder().build()).block();
    }
}
