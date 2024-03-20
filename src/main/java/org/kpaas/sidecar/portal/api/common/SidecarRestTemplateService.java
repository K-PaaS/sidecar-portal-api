package org.kpaas.sidecar.portal.api.common;

import org.container.platform.api.common.CommonService;
import org.container.platform.api.common.VaultService;
import org.container.platform.api.common.model.Params;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import static org.kpaas.sidecar.portal.api.common.Constants.TARGET_SIDECAR_API;

@Service("sidecarRestTemplateService")
public class SidecarRestTemplateService extends org.container.platform.api.common.RestTemplateService{

    /**
     * Instantiates a new Rest template service
     *
     * @param restTemplate                            the rest template
     * @param shortRestTemplate
     * @param propertyService                         the property service
     * @param commonService
     * @param vaultService
     * @param commonApiAuthorizationId
     * @param commonApiAuthorizationPassword
     * @param metricCollectorApiAuthorizationId
     * @param metricCollectorApiAuthorizationPassword
     */
    public SidecarRestTemplateService(RestTemplate restTemplate,
                               @Qualifier("shortTimeoutRestTemplate") RestTemplate shortRestTemplate,
                               @Qualifier("sidecarPropertyService") SidecarPropertyService propertyService,
                               CommonService commonService,
                               VaultService vaultService,
                               @Value("${commonApi.authorization.id}") String commonApiAuthorizationId,
                               @Value("${commonApi.authorization.password}") String commonApiAuthorizationPassword,
                               @Value("${cpMetricCollector.api.authorization.id}") String metricCollectorApiAuthorizationId,
                               @Value("${cpMetricCollector.api.authorization.password}") String metricCollectorApiAuthorizationPassword) {
        super(restTemplate, shortRestTemplate, propertyService, commonService, vaultService,
                commonApiAuthorizationId, commonApiAuthorizationPassword, metricCollectorApiAuthorizationId, metricCollectorApiAuthorizationPassword);
    }
    @Override
    protected void setApiUrlAuthorization(String reqApi, Params params) {

        super.setApiUrlAuthorization(reqApi, params);

        // SIDECAR API
        if(TARGET_SIDECAR_API.equals(reqApi)) {
            Assert.notNull(params, "Null parameter");
            Assert.notNull(params.getClusterToken(), "Null parameter(ClusterToken)");

            this.baseUrl = ((SidecarPropertyService)propertyService).getSidecarApiUrl();
            this.base64Authorization = "bearer " + params.getClusterToken();
        }
    }
}
