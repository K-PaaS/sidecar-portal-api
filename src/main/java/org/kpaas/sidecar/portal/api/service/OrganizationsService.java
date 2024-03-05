package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.organizations.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class OrganizationsService extends Common {
    public AssociateOrganizationAuditorResponse associateAuditor(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateAuditor(AssociateOrganizationAuditorRequest.builder().build()).block();
    }

    public AssociateOrganizationAuditorByUsernameResponse associateAuditorByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateAuditorByUsername(AssociateOrganizationAuditorByUsernameRequest.builder().build()).block();
    }

    public AssociateOrganizationBillingManagerResponse associateBillingManager(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateBillingManager(AssociateOrganizationBillingManagerRequest.builder().build()).block();
    }

    public AssociateOrganizationBillingManagerByUsernameResponse associateBillingManagerByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateBillingManagerByUsername(AssociateOrganizationBillingManagerByUsernameRequest.builder().build()).block();
    }

    public AssociateOrganizationManagerResponse associateManager(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateManager(AssociateOrganizationManagerRequest.builder().build()).block();
    }

    public AssociateOrganizationManagerByUsernameResponse associateManagerByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateManagerByUsername(AssociateOrganizationManagerByUsernameRequest.builder().build()).block();
    }

    public AssociateOrganizationPrivateDomainResponse associatePrivateDomain(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associatePrivateDomain(AssociateOrganizationPrivateDomainRequest.builder().build()).block();
    }

    public AssociateOrganizationUserResponse associateUser(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateUser(AssociateOrganizationUserRequest.builder().build()).block();
    }

    public AssociateOrganizationUserByUsernameResponse associateUserByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().associateUserByUsername(AssociateOrganizationUserByUsernameRequest.builder().build()).block();
    }

    public CreateOrganizationResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().create(CreateOrganizationRequest.builder().build()).block();
    }

    public DeleteOrganizationResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().delete(DeleteOrganizationRequest.builder().build()).block();
    }

    public GetOrganizationResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().get(GetOrganizationRequest.builder().build()).block();
    }

    public GetOrganizationInstanceUsageResponse getInstanceUsage(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().getInstanceUsage(GetOrganizationInstanceUsageRequest.builder().build()).block();
    }

    public GetOrganizationMemoryUsageResponse getMemoryUsage(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().getMemoryUsage(GetOrganizationMemoryUsageRequest.builder().build()).block();
    }

    public GetOrganizationUserRolesResponse getUserRoles(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().getUserRoles(GetOrganizationUserRolesRequest.builder().build()).block();
    }

    public ListOrganizationsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().list(ListOrganizationsRequest.builder().build()).block();
    }

    public ListOrganizationAuditorsResponse listAuditors(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listAuditors(ListOrganizationAuditorsRequest.builder().build()).block();
    }

    public ListOrganizationBillingManagersResponse listBillingManagers(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listBillingManagers(ListOrganizationBillingManagersRequest.builder().build()).block();
    }

    public ListOrganizationDomainsResponse listDomains(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listDomains(ListOrganizationDomainsRequest.builder().build()).block();
    }

    public ListOrganizationManagersResponse listManagers(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listManagers(ListOrganizationManagersRequest.builder().build()).block();
    }

    public ListOrganizationPrivateDomainsResponse listPrivateDomains(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listPrivateDomains(ListOrganizationPrivateDomainsRequest.builder().build()).block();
    }

    public ListOrganizationServicesResponse listServices(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listServices(ListOrganizationServicesRequest.builder().build()).block();
    }

    public ListOrganizationSpaceQuotaDefinitionsResponse listSpaceQuotaDefinitions(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listSpaceQuotaDefinitions(ListOrganizationSpaceQuotaDefinitionsRequest.builder().build()).block();
    }

    public ListOrganizationSpacesResponse listSpaces(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listSpaces(ListOrganizationSpacesRequest.builder().build()).block();
    }

    public ListOrganizationUsersResponse listUsers(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().listUsers(ListOrganizationUsersRequest.builder().build()).block();
    }

    public Void removeAuditor(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeAuditor(RemoveOrganizationAuditorRequest.builder().build()).block();
    }

    public Void removeAuditorByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeAuditorByUsername(RemoveOrganizationAuditorByUsernameRequest.builder().build()).block();
    }

    public Void removeBillingManager(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeBillingManager(RemoveOrganizationBillingManagerRequest.builder().build()).block();
    }

    public Void removeBillingManagerByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeBillingManagerByUsername(RemoveOrganizationBillingManagerByUsernameRequest.builder().build()).block();
    }

    public Void removeManager(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeManager(RemoveOrganizationManagerRequest.builder().build()).block();
    }

    public Void removeManagerByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeManagerByUsername(RemoveOrganizationManagerByUsernameRequest.builder().build()).block();
    }

    public Void removePrivateDomain(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removePrivateDomain(RemoveOrganizationPrivateDomainRequest.builder().build()).block();
    }

    public Void removeUser(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeUser(RemoveOrganizationUserRequest.builder().build()).block();
    }

    public Void removeUserByUsername(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().removeUserByUsername(RemoveOrganizationUserByUsernameRequest.builder().build()).block();
    }

    public SummaryOrganizationResponse summary(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().summary(SummaryOrganizationRequest.builder().build()).block();
    }

    public UpdateOrganizationResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizations().update(UpdateOrganizationRequest.builder().build()).block();
    }
}
