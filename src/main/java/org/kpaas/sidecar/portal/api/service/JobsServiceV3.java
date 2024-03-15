package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.jobs.GetJobRequest;
import org.cloudfoundry.client.v3.jobs.GetJobResponse;
import org.kpaas.sidecar.portal.api.common.Common;

public class JobsServiceV3 extends Common {
    public GetJobResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).jobsV3().get(GetJobRequest.builder().build()).block();
    }
}