package org.kpaas.sidecar.portal.api.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.servicebindings.CreateServiceBindingResponse;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsResponse;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingRelationships;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingType;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.model.ServiceBinding;
import org.kpaas.sidecar.portal.api.service.ServiceBindingsServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ServiceBindingsController extends Common {
    @Autowired
    private ServiceBindingsServiceV3 serviceBindingsServiceV3;

    @ApiOperation(value = "ServiceBinding 리스트 조회")
    @GetMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceBindings/list"})
    public ListServiceBindingsResponse list(@RequestParam(required = false) @ApiParam(value = "Application GUIDs", required = false)List<String> appGuids, @RequestParam(required = false) @ApiParam(value = "Application 이름들", required = false)List<String> appNames, @RequestParam(required = false) @ApiParam(value = "ServiceInstance GUIDs", required = false)List<String> serviceInstanceGuids, @RequestParam(required = false) @ApiParam(value = "ServiceInstance 이름들", required = false)List<String> serviceInstanceNames, @RequestParam(required = false) @ApiParam(value = "ServicePlan GUIDs", required = false)List<String> servicePlanGuids, @RequestParam(required = false) @ApiParam(value = "ServicePlan 이름들", required = false)List<String> servicePlanNames) throws Exception {
        return serviceBindingsServiceV3.list(appGuids, appNames, serviceInstanceGuids, serviceInstanceNames, servicePlanGuids, servicePlanNames);
    }

    @ApiOperation(value = "ServiceBinding 생성 (앱 - 서비스 연결)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appGuid", value = "Application GUID", required = true, paramType = "body", dataType = "string"),
            @ApiImplicitParam(name = "serviceGuid", value = "ServiceInstance GUID", required = true, paramType = "body", dataType = "string")
    })
    @PostMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceBindings"})
    public CreateServiceBindingResponse create(@RequestBody @ApiParam(hidden = true)Map<String, String> requestData) throws Exception {
        // name 이 꼭 필요한지 확인 필요
        String appGuid = stringNullCheck(requestData.get("appGuid"));
        String serviceGuid = stringNullCheck(requestData.get("serviceGuid"));
        if ( appGuid.isEmpty() || serviceGuid.isEmpty() ){ // 차후 수정

            throw new NullPointerException("NULL 발생");
        }
        ServiceBinding serviceBinding = new ServiceBinding();
        serviceBinding.setType(ServiceBindingType.APPLICATION);
        serviceBinding.setRelationships(ServiceBindingRelationships.builder()
                .application(ToOneRelationship.builder().data(Relationship.builder().id(appGuid).build()).build())
                .serviceInstance(ToOneRelationship.builder().data(Relationship.builder().id(serviceGuid).build()).build())
                .build());
        return serviceBindingsServiceV3.create(serviceBinding);
    }

    @ApiOperation(value = "ServiceBinding 삭제 (앱 - 서비스 연결해제)")
    @DeleteMapping(value = {Constants.URI_SIDECAR_API_PREFIX + "/serviceBindings/{serviceBindingGuid}"})
    public Map delete(@PathVariable @ApiParam(value = "ServiceBinding GUID", required = true)String serviceBindingGuid) throws Exception {
        Map map = new HashMap();
        String result = serviceBindingsServiceV3.delete(serviceBindingGuid);
        map.put("resultMessage", result);
        return map;
    }
}
