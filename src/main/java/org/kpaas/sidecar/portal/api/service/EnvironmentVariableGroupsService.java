package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.environmentvariablegroups.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class EnvironmentVariableGroupsService extends Common {
    public GetRunningEnvironmentVariablesResponse getRunningEnvironmentVariables(String guid) {
        return cloudFoundryClient(tokenProvider()).environmentVariableGroups().getRunningEnvironmentVariables(GetRunningEnvironmentVariablesRequest.builder().build()).block();
    }

    public GetStagingEnvironmentVariablesResponse getStagingEnvironmentVariables(String guid) {
        return cloudFoundryClient(tokenProvider()).environmentVariableGroups().getStagingEnvironmentVariables(GetStagingEnvironmentVariablesRequest.builder().build()).block();
    }

    public UpdateRunningEnvironmentVariablesResponse updateRunningEnvironmentVariables(String guid) {
        return cloudFoundryClient(tokenProvider()).environmentVariableGroups().updateRunningEnvironmentVariables(UpdateRunningEnvironmentVariablesRequest.builder().build()).block();
    }

    public UpdateStagingEnvironmentVariablesResponse updateStagingEnvironmentVariables(String guid) {
        return cloudFoundryClient(tokenProvider()).environmentVariableGroups().updateStagingEnvironmentVariables(UpdateStagingEnvironmentVariablesRequest.builder().build()).block();
    }
}
