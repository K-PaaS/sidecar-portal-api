package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.users.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class UsersService extends Common {
    public AssociateUserAuditedOrganizationResponse associateAuditedOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().associateAuditedOrganization(AssociateUserAuditedOrganizationRequest.builder().build()).block();
    }

    public AssociateUserAuditedSpaceResponse associateAuditedSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().associateAuditedSpace(AssociateUserAuditedSpaceRequest.builder().build()).block();
    }

    public AssociateUserBillingManagedOrganizationResponse associateBillingManagedOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().associateBillingManagedOrganization(AssociateUserBillingManagedOrganizationRequest.builder().build()).block();
    }

    public AssociateUserManagedOrganizationResponse associateManagedOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().associateManagedOrganization(AssociateUserManagedOrganizationRequest.builder().build()).block();
    }

    public AssociateUserManagedSpaceResponse associateManagedSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().associateManagedSpace(AssociateUserManagedSpaceRequest.builder().build()).block();
    }

    public AssociateUserOrganizationResponse associateOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().associateOrganization(AssociateUserOrganizationRequest.builder().build()).block();
    }

    public AssociateUserSpaceResponse associateSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().associateSpace(AssociateUserSpaceRequest.builder().build()).block();
    }

    public CreateUserResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().create(CreateUserRequest.builder().build()).block();
    }

    public DeleteUserResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().delete(DeleteUserRequest.builder().build()).block();
    }

    public GetUserResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().get(GetUserRequest.builder().build()).block();
    }

    public ListUsersResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().list(ListUsersRequest.builder().build()).block();
    }

    public ListUserAuditedOrganizationsResponse listAuditedOrganizations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().listAuditedOrganizations(ListUserAuditedOrganizationsRequest.builder().build()).block();
    }

    public ListUserAuditedSpacesResponse listAuditedSpaces(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().listAuditedSpaces(ListUserAuditedSpacesRequest.builder().build()).block();
    }

    public ListUserBillingManagedOrganizationsResponse listBillingManagedOrganizations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().listBillingManagedOrganizations(ListUserBillingManagedOrganizationsRequest.builder().build()).block();
    }

    public ListUserManagedOrganizationsResponse listManagedOrganizations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().listManagedOrganizations(ListUserManagedOrganizationsRequest.builder().build()).block();
    }

    public ListUserManagedSpacesResponse listManagedSpaces(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().listManagedSpaces(ListUserManagedSpacesRequest.builder().build()).block();
    }

    public ListUserOrganizationsResponse listOrganizations(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().listOrganizations(ListUserOrganizationsRequest.builder().build()).block();
    }

    public ListUserSpacesResponse listSpaces(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().listSpaces(ListUserSpacesRequest.builder().build()).block();
    }

    public Void removeAuditedOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().removeAuditedOrganization(RemoveUserAuditedOrganizationRequest.builder().build()).block();
    }

    public Void removeAuditedSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().removeAuditedSpace(RemoveUserAuditedSpaceRequest.builder().build()).block();
    }

    public Void removeBillingManagedOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().removeBillingManagedOrganization(RemoveUserBillingManagedOrganizationRequest.builder().build()).block();
    }

    public Void removeManagedOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().removeManagedOrganization(RemoveUserManagedOrganizationRequest.builder().build()).block();
    }

    public Void removeManagedSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().removeManagedSpace(RemoveUserManagedSpaceRequest.builder().build()).block();
    }

    public Void removeOrganization(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().removeOrganization(RemoveUserOrganizationRequest.builder().build()).block();
    }

    public Void removeSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().removeSpace(RemoveUserSpaceRequest.builder().build()).block();
    }

    public SummaryUserResponse summary(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().summary(SummaryUserRequest.builder().build()).block();
    }

    public UpdateUserResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).users().update(UpdateUserRequest.builder().build()).block();
    }
}
