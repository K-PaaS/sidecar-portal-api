package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.domains.CreateDomainResponse;
import org.cloudfoundry.client.v3.domains.GetDomainResponse;
import org.cloudfoundry.client.v3.domains.ListDomainsResponse;
import org.kpaas.sidecar.portal.api.model.Domain;
import org.kpaas.sidecar.portal.api.service.DomainsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DomainsController {
    @Autowired
    private DomainsServiceV3 domainsServiceV3;

    @PostMapping(value = {"/domains/create"})
    public CreateDomainResponse create(@RequestBody Domain domain, String token) throws Exception {
        return domainsServiceV3.create(domain ,token);
    }

    @DeleteMapping(value = {"/domains/{domainGuid}/delete"})
    public String delete(@PathVariable String domainGuid, String token) throws Exception {
        return domainsServiceV3.delete(domainGuid ,token);
    }

    @GetMapping(value = {"/domains/{domainGuid}/get"})
    public GetDomainResponse get(@PathVariable String domainGuid, String token) throws Exception {
        return domainsServiceV3.get(domainGuid ,token);
    }

    @GetMapping(value = {"/domains/list"})
    public ListDomainsResponse list(String token) throws Exception {
        return domainsServiceV3.list(token);
    }
}
