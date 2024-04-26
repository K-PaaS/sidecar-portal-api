package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.organizations.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.Organization;
import org.kpaas.sidecar.portal.api.service.OrganizationsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrganizationsController extends Common {
    @Autowired
    private OrganizationsServiceV3 organizationsServiceV3;

    @ApiOperation(value = "Org 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Org 이름", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/organizations"})
    public CreateOrganizationResponse create(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
        String name = stringNullCheck(requestData.get("name"));
        if ( name.isEmpty() ){ // 차후 수정
            throw new NullPointerException("NULL 발생");
        }
        Organization org = new Organization();
        org.setName(name);
        return organizationsServiceV3.create(org);
    }

    @ApiOperation(value = "Org 삭제")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/organizations/{orgGuid}"})
    public Map delete(@PathVariable @ApiParam(value = "Org GUID", required = true)String orgGuid, String token) throws Exception {
        Map map = new HashMap();
        String result = organizationsServiceV3.delete(orgGuid);
        map.put("resultMessage", result);
        return map;
    }

    @ApiOperation(value = "Org 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/organizations/{orgGuid}/get"})
    public GetOrganizationResponse get(@PathVariable @ApiParam(value = "Org GUID", required = true)String orgGuid) throws Exception {
        return organizationsServiceV3.get(orgGuid);
    }

    @ApiOperation(value = "Org 목록 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/organizations/list"})
    public ListOrganizationsResponse list(@RequestParam(required = false) @ApiParam(value = "Org 이름들", required = false)List<String> names) throws Exception {
        return organizationsServiceV3.list(names);
    }

    @ApiOperation(value = "Org Domain 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/organizations/{orgGuid}/listDomains"})
    public ListOrganizationDomainsResponse listDomains(@PathVariable @ApiParam(value = "Org GUID", required = true)String orgGuid, @RequestParam(required = false) @ApiParam(value = "Domain Guids", required = false)List<String> domainGuids, @RequestParam(required = false) @ApiParam(value = "Org 이름들", required = false)List<String> names) throws Exception {
        return organizationsServiceV3.listDomains(orgGuid, domainGuids, names);
    }

    @ApiOperation(value = "Org Default Domain 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/organizations/{orgGuid}/getDefaultDomain"})
    public GetOrganizationDefaultDomainResponse getDefaultDomain(@PathVariable @ApiParam(value = "Org GUID", required = true) String orgGuid) throws Exception {
        return organizationsServiceV3.getDefaultDomain(orgGuid);
    }
}
