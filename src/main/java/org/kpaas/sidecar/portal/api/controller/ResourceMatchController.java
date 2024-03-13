package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.resourcematch.ListMatchingResourcesResponse;
import org.kpaas.sidecar.portal.api.service.ResourceMatchServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceMatchController {
    @Autowired
    private ResourceMatchServiceV3 resourceMatchServiceV3;

    //미 완성
    @GetMapping(value = {"/resourceMatch/list"})
    public ListMatchingResourcesResponse list(String token) throws Exception {
        return resourceMatchServiceV3.list(token);
    }
}
