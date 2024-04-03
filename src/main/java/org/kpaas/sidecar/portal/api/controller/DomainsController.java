package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "Domain 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Domain 이름", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains"})
    public CreateDomainResponse create(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        if ( name.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }
        Domain domain = new Domain();
        domain.setName(name);

        return domainsServiceV3.create(domain);
    }

    @ApiOperation(value = "Domain 삭제")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains/{domainGuid}"})
    public String delete(@PathVariable @ApiParam(value = "Domain GUID", required = true)String domainGuid) throws Exception {
        return domainsServiceV3.delete(domainGuid);
    }

    @ApiOperation(value = "Domain 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains/{domainGuid}/get"})
    public GetDomainResponse get(@PathVariable @ApiParam(value = "Domain GUID", required = true)String domainGuid) throws Exception {
        return domainsServiceV3.get(domainGuid);
    }

    @ApiOperation(value = "Domain 목록 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/domains/list"})
    public ListDomainsResponse list(@RequestParam(required = false) @ApiParam(value = "Domain 이름들", required = false)List<String> names, @RequestParam(required = false) @ApiParam(value = "보유 Org GUIDs", required = false)List<String> owningOrgGuids) throws Exception {
        return domainsServiceV3.list(names, owningOrgGuids);
    }
}
