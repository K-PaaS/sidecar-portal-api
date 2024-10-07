package org.kpaas.sidecar.portal.api.common;

import org.container.platform.api.common.CommonService;
import org.container.platform.api.common.VaultService;
import org.container.platform.api.common.model.Params;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *  SidecarRestTemplate Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("SidecarRestTemplateService 테스트")
class SidecarRestTemplateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplate shortRestTemplate;

    @Mock
    private SidecarPropertyService propertyService;

    @Mock
    private CommonService commonService;

    @Mock
    private VaultService vaultService;

    private SidecarRestTemplateService sidecarRestTemplateService;

    @BeforeEach
    void setUp() {
        sidecarRestTemplateService = new SidecarRestTemplateService(
                restTemplate,
                shortRestTemplate,
                propertyService,
                commonService,
                vaultService,
                "commonApiId",
                "commonApiPassword",
                "metricCollectorId",
                "metricCollectorPassword"
        );
    }

    @Test
    @DisplayName("SIDECAR API 요청 시 올바른 URL과 인증 정보 설정")
    void setApiUrlAuthorization_withSidecarApi() {
        // Given
        String reqApi = Constants.TARGET_SIDECAR_API;
        Params params = new Params();
        params.setClusterToken("testToken");
        String expectedUrl = "http://sidecar-api-url.com";

        when(propertyService.getSidecarApiUrl()).thenReturn(expectedUrl);

        // When
        sidecarRestTemplateService.setApiUrlAuthorization(reqApi, params);

        // Then
        assertEquals(expectedUrl, ReflectionTestUtils.getField(sidecarRestTemplateService, "baseUrl"));
        assertEquals("bearer testToken", ReflectionTestUtils.getField(sidecarRestTemplateService, "base64Authorization"));
        verify(propertyService, times(1)).getSidecarApiUrl();
    }

    @Test
    @DisplayName("Null 파라미터로 SIDECAR API 요청 시 예외 발생")
    void setApiUrlAuthorization_withNullParams() {
        // Given
        String reqApi = Constants.TARGET_SIDECAR_API;

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                sidecarRestTemplateService.setApiUrlAuthorization(reqApi, null)
        );
    }

    @Test
    @DisplayName("Null ClusterToken으로 SIDECAR API 요청 시 예외 발생")
    void setApiUrlAuthorization_withNullClusterToken() {
        // Given
        String reqApi = Constants.TARGET_SIDECAR_API;
        Params params = new Params();
        params.setClusterToken(null);

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                sidecarRestTemplateService.setApiUrlAuthorization(reqApi, params)
        );
    }

    @Test
    @DisplayName("SIDECAR API가 아닌 다른 API 요청 시 처리")
    void setApiUrlAuthorization_withNonSidecarApi() {
        // Given
        String reqApi = "NON_SIDECAR_API";
        Params params = new Params();
        params.setClusterToken("testToken");

        // When
        sidecarRestTemplateService.setApiUrlAuthorization(reqApi, params);

        // Then
        assertNotEquals("bearer testToken", ReflectionTestUtils.getField(sidecarRestTemplateService, "base64Authorization"));
        verify(propertyService, never()).getSidecarApiUrl();
    }

    @Test
    @DisplayName("빈 ClusterToken으로 SIDECAR API 요청 시 예외 발생")
    void setApiUrlAuthorization_withEmptyClusterToken() {
        // Given
        String reqApi = Constants.TARGET_SIDECAR_API;
        Params params = new Params();
        params.setClusterToken("");

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                sidecarRestTemplateService.setApiUrlAuthorization(reqApi, params)
        );
    }
}