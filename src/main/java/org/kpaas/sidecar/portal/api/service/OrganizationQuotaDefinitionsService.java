package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.organizationquotadefinitions.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class OrganizationQuotaDefinitionsService extends Common {
    public CreateOrganizationQuotaDefinitionResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationQuotaDefinitions().create(CreateOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
    public DeleteOrganizationQuotaDefinitionResponse delete(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationQuotaDefinitions().delete(DeleteOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
    public GetOrganizationQuotaDefinitionResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationQuotaDefinitions().get(GetOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
    public ListOrganizationQuotaDefinitionsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationQuotaDefinitions().list(ListOrganizationQuotaDefinitionsRequest.builder().build()).block();
    }
    public UpdateOrganizationQuotaDefinitionResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).organizationQuotaDefinitions().update(UpdateOrganizationQuotaDefinitionRequest.builder().build()).block();
    }
}
