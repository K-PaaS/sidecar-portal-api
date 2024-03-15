package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.users.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class UsersService extends Common {
    public AssociateUserAuditedOrganizationResponse associateAuditedOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().associateAuditedOrganization(AssociateUserAuditedOrganizationRequest.builder().build()).block();
    }

    public AssociateUserAuditedSpaceResponse associateAuditedSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).users().associateAuditedSpace(AssociateUserAuditedSpaceRequest.builder().build()).block();
    }

    public AssociateUserBillingManagedOrganizationResponse associateBillingManagedOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().associateBillingManagedOrganization(AssociateUserBillingManagedOrganizationRequest.builder().build()).block();
    }

    public AssociateUserManagedOrganizationResponse associateManagedOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().associateManagedOrganization(AssociateUserManagedOrganizationRequest.builder().build()).block();
    }

    public AssociateUserManagedSpaceResponse associateManagedSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).users().associateManagedSpace(AssociateUserManagedSpaceRequest.builder().build()).block();
    }

    public AssociateUserOrganizationResponse associateOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().associateOrganization(AssociateUserOrganizationRequest.builder().build()).block();
    }

    public AssociateUserSpaceResponse associateSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).users().associateSpace(AssociateUserSpaceRequest.builder().build()).block();
    }

    public CreateUserResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).users().create(CreateUserRequest.builder().build()).block();
    }

    public DeleteUserResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).users().delete(DeleteUserRequest.builder().build()).block();
    }

    public GetUserResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).users().get(GetUserRequest.builder().build()).block();
    }

    public ListUsersResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).users().list(ListUsersRequest.builder().build()).block();
    }

    public ListUserAuditedOrganizationsResponse listAuditedOrganizations(String guid) {
        return cloudFoundryClient(tokenProvider()).users().listAuditedOrganizations(ListUserAuditedOrganizationsRequest.builder().build()).block();
    }

    public ListUserAuditedSpacesResponse listAuditedSpaces(String guid) {
        return cloudFoundryClient(tokenProvider()).users().listAuditedSpaces(ListUserAuditedSpacesRequest.builder().build()).block();
    }

    public ListUserBillingManagedOrganizationsResponse listBillingManagedOrganizations(String guid) {
        return cloudFoundryClient(tokenProvider()).users().listBillingManagedOrganizations(ListUserBillingManagedOrganizationsRequest.builder().build()).block();
    }

    public ListUserManagedOrganizationsResponse listManagedOrganizations(String guid) {
        return cloudFoundryClient(tokenProvider()).users().listManagedOrganizations(ListUserManagedOrganizationsRequest.builder().build()).block();
    }

    public ListUserManagedSpacesResponse listManagedSpaces(String guid) {
        return cloudFoundryClient(tokenProvider()).users().listManagedSpaces(ListUserManagedSpacesRequest.builder().build()).block();
    }

    public ListUserOrganizationsResponse listOrganizations(String guid) {
        return cloudFoundryClient(tokenProvider()).users().listOrganizations(ListUserOrganizationsRequest.builder().build()).block();
    }

    public ListUserSpacesResponse listSpaces(String guid) {
        return cloudFoundryClient(tokenProvider()).users().listSpaces(ListUserSpacesRequest.builder().build()).block();
    }

    public Void removeAuditedOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().removeAuditedOrganization(RemoveUserAuditedOrganizationRequest.builder().build()).block();
    }

    public Void removeAuditedSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).users().removeAuditedSpace(RemoveUserAuditedSpaceRequest.builder().build()).block();
    }

    public Void removeBillingManagedOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().removeBillingManagedOrganization(RemoveUserBillingManagedOrganizationRequest.builder().build()).block();
    }

    public Void removeManagedOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().removeManagedOrganization(RemoveUserManagedOrganizationRequest.builder().build()).block();
    }

    public Void removeManagedSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).users().removeManagedSpace(RemoveUserManagedSpaceRequest.builder().build()).block();
    }

    public Void removeOrganization(String guid) {
        return cloudFoundryClient(tokenProvider()).users().removeOrganization(RemoveUserOrganizationRequest.builder().build()).block();
    }

    public Void removeSpace(String guid) {
        return cloudFoundryClient(tokenProvider()).users().removeSpace(RemoveUserSpaceRequest.builder().build()).block();
    }

    public SummaryUserResponse summary(String guid) {
        return cloudFoundryClient(tokenProvider()).users().summary(SummaryUserRequest.builder().build()).block();
    }

    public UpdateUserResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).users().update(UpdateUserRequest.builder().build()).block();
    }
}
