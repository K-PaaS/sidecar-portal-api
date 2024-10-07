package org.container.platform.api.common;

import org.container.platform.api.common.model.Params;
import org.container.platform.api.common.model.ResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  RestTemplate Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("RestTemplateService 테스트")
class RestTemplateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplate shortRestTemplate;

    private RestTemplateService restTemplateService;

    @BeforeEach
    void setUp() {
        restTemplateService = new RestTemplateService(
                restTemplate,
                shortRestTemplate,
                mock(PropertyService.class),
                mock(CommonService.class),
                mock(VaultService.class),
                "testId",
                "testPassword",
                "metricId",
                "metricPassword"
        );
    }

    @Test
    @DisplayName("일반 HTTP 요청 전송 테스트")
    void send() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer test-token");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"key\":\"value\"}", headers);

        ResultStatus expectedResult = new ResultStatus();
        expectedResult.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        )).thenReturn(new ResponseEntity<>(expectedResult, HttpStatus.OK));

        // When
        ResultStatus result = restTemplateService.send("POST", "/test", HttpMethod.POST, entity, ResultStatus.class, new Params());

        // Then
        assertEquals(Constants.RESULT_STATUS_SUCCESS, result.getResultCode());
        verify(restTemplate).exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        );
    }

    @Test
    @DisplayName("관리자 권한으로 HTTP 요청 전송 테스트")
    void sendAdmin() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"key\":\"value\"}", headers);

        ResultStatus expectedResult = new ResultStatus();
        expectedResult.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        )).thenReturn(new ResponseEntity<>(expectedResult, HttpStatus.OK));

        // When
        ResultStatus result = restTemplateService.sendAdmin("POST", "/admin", HttpMethod.POST, entity, ResultStatus.class, "testToken", "testTokenName", new Params());

        // Then
        assertEquals(Constants.RESULT_STATUS_SUCCESS, result.getResultCode());
        verify(restTemplate).exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        );
    }

    @Test
    @DisplayName("Ping 요청 전송 테스트")
    void sendPing() {
        // Given
        ResultStatus expectedResult = new ResultStatus();
        expectedResult.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(shortRestTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        )).thenReturn(new ResponseEntity<>(expectedResult, HttpStatus.OK));

        // When
        ResultStatus result = restTemplateService.sendPing("/ping", ResultStatus.class, new Params());

        // Then
        assertEquals(Constants.RESULT_STATUS_SUCCESS, result.getResultCode());
        verify(shortRestTemplate).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        );
    }

    @Test
    @DisplayName("YAML 형식 데이터 요청 전송 테스트")
    void sendYaml() {
        // Given
        ResultStatus expectedResult = new ResultStatus();
        expectedResult.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        )).thenReturn(new ResponseEntity<>(expectedResult, HttpStatus.OK));

        // When
        ResultStatus result = restTemplateService.sendYaml("GET", "/yaml", HttpMethod.GET, ResultStatus.class, new Params());

        // Then
        assertEquals(Constants.RESULT_STATUS_SUCCESS, result.getResultCode());
        verify(restTemplate).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ResultStatus.class)
        );
    }
}