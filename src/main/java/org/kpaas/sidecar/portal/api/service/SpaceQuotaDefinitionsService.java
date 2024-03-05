package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.spacequotadefinitions.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class SpaceQuotaDefinitionsService extends Common {
    public AssociateSpaceQuotaDefinitionResponse associateSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().associateSpace(AssociateSpaceQuotaDefinitionRequest.builder().build()).block();
    }

    public CreateSpaceQuotaDefinitionResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().create(CreateSpaceQuotaDefinitionRequest.builder().build()).block();
    }

    public DeleteSpaceQuotaDefinitionResponse delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().delete(DeleteSpaceQuotaDefinitionRequest.builder().build()).block();
    }

    public GetSpaceQuotaDefinitionResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().get(GetSpaceQuotaDefinitionRequest.builder().build()).block();
    }

    public ListSpaceQuotaDefinitionsResponse list(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().list(ListSpaceQuotaDefinitionsRequest.builder().build()).block();
    }

    public ListSpaceQuotaDefinitionSpacesResponse listSpaces(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().listSpaces(ListSpaceQuotaDefinitionSpacesRequest.builder().build()).block();
    }

    public Void removeSpace(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().removeSpace(RemoveSpaceQuotaDefinitionRequest.builder().build()).block();
    }

    public UpdateSpaceQuotaDefinitionResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).spaceQuotaDefinitions().update(UpdateSpaceQuotaDefinitionRequest.builder().build()).block();
    }
}
