package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.organizationquotadefinitions.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class OrganizationQuotaDefinitionsService extends Common {
    public CreateOrganizationQuotaDefinitionResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationQuotaDefinitions().create(CreateOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
    public DeleteOrganizationQuotaDefinitionResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationQuotaDefinitions().delete(DeleteOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
    public GetOrganizationQuotaDefinitionResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationQuotaDefinitions().get(GetOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
    public ListOrganizationQuotaDefinitionsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationQuotaDefinitions().list(ListOrganizationQuotaDefinitionsRequest.builder().build()).block();
    }
    public UpdateOrganizationQuotaDefinitionResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).organizationQuotaDefinitions().update(UpdateOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
}
