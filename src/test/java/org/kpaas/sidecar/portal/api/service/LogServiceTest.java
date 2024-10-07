//package org.kpaas.sidecar.portal.api.service;
//
//import org.cloudfoundry.reactor.TokenProvider;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.kpaas.sidecar.portal.api.common.Constants;
//import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
//import org.kpaas.sidecar.portal.api.common.model.Params;
//import org.kpaas.sidecar.portal.api.login.AuthUtil;
//import org.kpaas.sidecar.portal.api.model.Batch;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpMethod;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class LogServiceTest {
//
//    @Mock
//    private SidecarRestTemplateService restTemplateService;
//
//    @Mock
//    private AuthUtil authUtil;
//
//    @Mock
//    private TokenProvider tokenProvider;
//
//    private LogService logService;
//
//    @BeforeEach
//    void setUp() {
//        logService = new LogService(restTemplateService) {
//            @Override
//            public TokenProvider tokenProvider() {
//                return tokenProvider;
//            }
//        };
//    }
//
//    @Test
//    void testGetLog() {
//        // Given
//        String guid = "testGuid";
//        String time = "2024-10-04T12:00:00Z";
//        int limit = 100;
//        boolean isDescending = true;
//        String envelopeTypes = "LOG";
//
//        Map<String, Object> mockResponse = new HashMap<>();
//        Batch expectedBatch = new Batch();
//        mockResponse.put("envelopes", expectedBatch);
//
//        Params mockParams = new Params();
//        when(authUtil.sidecarAuth()).thenReturn(mockParams);
//
//        when(restTemplateService.send(eq(Constants.TARGET_SIDECAR_API),
//                anyString(),
//                eq(HttpMethod.GET),
//                isNull(),
//                eq(Map.class),
//                any(Params.class)))
//                .thenReturn(mockResponse);
//
//        // When
//        Batch result = logService.getLog(guid, time, limit, isDescending, envelopeTypes);
//
//        // Then
//        assertEquals(expectedBatch, result);
//
//        verify(restTemplateService).send(
//                eq(Constants.TARGET_SIDECAR_API),
//                eq("/api/v1/read/" + guid + "?descending=true&envelope_types=" + envelopeTypes + "&limit=" + limit + "&start_time=" + time),
//                eq(HttpMethod.GET),
//                isNull(),
//                eq(Map.class),
//                any(Params.class)
//        );
//    }
//
//    @Test
//    void testGetLogWithZeroLimit() {
//        // Given
//        String guid = "testGuid";
//        String time = "2024-10-04T12:00:00Z";
//        int limit = 0;
//        boolean isDescending = false;
//        String envelopeTypes = "LOG";
//
//        Map<String, Object> mockResponse = new HashMap<>();
//        Batch expectedBatch = new Batch();
//        mockResponse.put("envelopes", expectedBatch);
//
//        Params mockParams = new Params();
//        when(authUtil.sidecarAuth()).thenReturn(mockParams);
//
//        when(restTemplateService.send(eq(Constants.TARGET_SIDECAR_API),
//                anyString(),
//                eq(HttpMethod.GET),
//                isNull(),
//                eq(Map.class),
//                any(Params.class)))
//                .thenReturn(mockResponse);
//
//        // When
//        Batch result = logService.getLog(guid, time, limit, isDescending, envelopeTypes);
//
//        // Then
//        assertEquals(expectedBatch, result);
//
//        verify(restTemplateService).send(
//                eq(Constants.TARGET_SIDECAR_API),
//                eq("/api/v1/read/" + guid + "?envelope_types=" + envelopeTypes + "&start_time=" + time),
//                eq(HttpMethod.GET),
//                isNull(),
//                eq(Map.class),
//                any(Params.class)
//        );
//    }
//}