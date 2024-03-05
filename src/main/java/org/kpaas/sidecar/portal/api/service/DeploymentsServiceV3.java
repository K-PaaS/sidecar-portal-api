package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.deployments.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;

@Service
public class DeploymentsServiceV3 extends Common {
    public CancelDeploymentResponse cancel(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .deploymentsV3()
                .cancel(CancelDeploymentRequest
                        .builder()
                        .deploymentId(guid)
                        .build())
                .block();
    }

    public CreateDeploymentResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).deploymentsV3().create(CreateDeploymentRequest.builder().build()).block();
    }

    public GetDeploymentResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .deploymentsV3()
                .get(GetDeploymentRequest
                        .builder()
                        .deploymentId(guid)
                        .build())
                .block();
    }

    public ListDeploymentsResponse list(String token) {
        return cloudFoundryClient(tokenProvider(token)).deploymentsV3().list(ListDeploymentsRequest.builder().build()).block();
    }
}
