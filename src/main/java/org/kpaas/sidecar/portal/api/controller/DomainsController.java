package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.domains.CreateDomainResponse;
import org.cloudfoundry.client.v3.domains.GetDomainResponse;
import org.cloudfoundry.client.v3.domains.ListDomainsResponse;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Domain;
import org.kpaas.sidecar.portal.api.service.DomainsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DomainsController extends Common {
    @Autowired
    private DomainsServiceV3 domainsServiceV3;

    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains"})
    public CreateDomainResponse create(@RequestBody Map<String, String> requestData) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        if ( name.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }
        Domain domain = new Domain();
        domain.setName(name);

        return domainsServiceV3.create(domain);
    }

    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains/{domainGuid}"})
    public String delete(@PathVariable String domainGuid) throws Exception {
        return domainsServiceV3.delete(domainGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains/{domainGuid}/get"})
    public GetDomainResponse get(@PathVariable String domainGuid) throws Exception {
        return domainsServiceV3.get(domainGuid);
    }

    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains/list"})
    public ListDomainsResponse list(@RequestParam(required = false) List<String> names, @RequestParam(required = false) List<String> owningOrgGuids) throws Exception {
        return domainsServiceV3.list(names, owningOrgGuids);
    }
}
