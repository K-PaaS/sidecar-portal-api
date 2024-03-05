package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.environmentvariablegroups.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class EnvironmentVariableGroupsService extends Common {
    public GetRunningEnvironmentVariablesResponse getRunningEnvironmentVariables(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).environmentVariableGroups().getRunningEnvironmentVariables(GetRunningEnvironmentVariablesRequest.builder().build()).block();
    }

    public GetStagingEnvironmentVariablesResponse getStagingEnvironmentVariables(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).environmentVariableGroups().getStagingEnvironmentVariables(GetStagingEnvironmentVariablesRequest.builder().build()).block();
    }

    public UpdateRunningEnvironmentVariablesResponse updateRunningEnvironmentVariables(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).environmentVariableGroups().updateRunningEnvironmentVariables(UpdateRunningEnvironmentVariablesRequest.builder().build()).block();
    }

    public UpdateStagingEnvironmentVariablesResponse updateStagingEnvironmentVariables(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).environmentVariableGroups().updateStagingEnvironmentVariables(UpdateStagingEnvironmentVariablesRequest.builder().build()).block();
    }
}
