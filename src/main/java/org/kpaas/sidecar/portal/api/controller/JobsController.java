package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.jobs.GetJobResponse;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.service.JobsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobsController {
    @Autowired
    private JobsServiceV3 jobsServiceV3;

    @ApiOperation(value = "Job 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/jobs/{jobGuid}"})
    public GetJobResponse get(@PathVariable @ApiParam(value = "job GUID", required = true)String jobGuid) throws Exception {
        return jobsServiceV3.get(jobGuid);
    }
}
