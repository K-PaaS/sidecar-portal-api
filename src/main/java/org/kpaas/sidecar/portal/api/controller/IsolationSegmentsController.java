package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.isolationsegments.CreateIsolationSegmentResponse;
import org.cloudfoundry.client.v3.isolationsegments.ListIsolationSegmentsResponse;
import org.kpaas.sidecar.portal.api.model.IsolationSegment;
import org.kpaas.sidecar.portal.api.service.IsolationSegmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IsolationSegmentsController {
    @Autowired
    private IsolationSegmentsService isolationSegmentsService;

    @PostMapping(value = {"/isolation_segments/create"})
    public CreateIsolationSegmentResponse create(@RequestBody IsolationSegment isolationSegment, String token) throws Exception {
        return isolationSegmentsService.create(isolationSegment, token);
    }

    @GetMapping(value = {"/isolation_segments/list"})
    public ListIsolationSegmentsResponse list(String token) throws Exception {
        return isolationSegmentsService.list(token);
    }
}
