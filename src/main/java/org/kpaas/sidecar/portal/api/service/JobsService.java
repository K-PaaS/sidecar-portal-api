package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.jobs.GetJobRequest;
import org.cloudfoundry.client.v2.jobs.GetJobResponse;
import org.kpaas.sidecar.portal.api.common.Common;

public class JobsService extends Common {
    public GetJobResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).jobs().get(GetJobRequest.builder().build()).block();
    }
}
