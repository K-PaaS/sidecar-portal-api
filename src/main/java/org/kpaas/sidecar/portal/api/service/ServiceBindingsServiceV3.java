package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.servicebindings.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.ServiceBinding;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceBindingsServiceV3 extends Common {
    public CreateServiceBindingResponse create(ServiceBinding serviceBinding, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .serviceBindingsV3()
                .create(CreateServiceBindingRequest
                        .builder()
                        .name(serviceBinding.getName())
                        .type(serviceBinding.getType())
                        .relationships(serviceBinding.getRelationships())
                        .build())
                .block();
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .serviceBindingsV3()
                .delete(DeleteServiceBindingRequest
                        .builder()
                        .serviceBindingId(guid)
                        .build())
                .block();
    }

    public GetServiceBindingResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBindingsV3().get(GetServiceBindingRequest.builder().build()).block();
    }

    public GetServiceBindingDetailsResponse getDetails(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBindingsV3().getDetails(GetServiceBindingDetailsRequest.builder().build()).block();
    }

    public GetServiceBindingParametersResponse getParameters(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBindingsV3().getParameters(GetServiceBindingParametersRequest.builder().build()).block();
    }

    public ListServiceBindingsResponse list(List<String> appGuids, List<String> appNames, List<String> serviceInstanceGuids, List<String> serviceInstanceNames, List<String> servicePlanGuids, List<String> servicePlanNames, String token) {
        appGuids = stringListNullCheck(appGuids);
        appNames = stringListNullCheck(appNames);
        serviceInstanceGuids = stringListNullCheck(serviceInstanceGuids);
        serviceInstanceNames = stringListNullCheck(serviceInstanceNames);
        servicePlanGuids = stringListNullCheck(servicePlanGuids);
        servicePlanNames = stringListNullCheck(servicePlanNames);

        return cloudFoundryClient(tokenProvider(token))
                .serviceBindingsV3()
                .list(ListServiceBindingsRequest
                        .builder()
                        .applicationIds(appGuids)
                        .appNames(appNames)
                        .serviceInstanceIds(serviceInstanceGuids)
                        .serviceInstanceNames(serviceInstanceNames)
                        .servicePlanIds(servicePlanGuids)
                        .servicePlanNames(servicePlanNames)
                        .build())
                .block();
    }

    public UpdateServiceBindingResponse update(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).serviceBindingsV3().update(UpdateServiceBindingRequest.builder().build()).block();
    }
}
