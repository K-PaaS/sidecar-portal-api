package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.jobs.GetJobRequest;
import org.cloudfoundry.client.v3.jobs.GetJobResponse;
import org.cloudfoundry.client.v3.jobs.JobState;
import org.cloudfoundry.client.v3.jobs.JobsV3;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *  Jobs Service V3 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("JobsServiceV3 테스트")
public class JobsServiceV3Test {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @Mock
    private JobsV3 jobsV3;

    @InjectMocks
    private JobsServiceV3 jobsServiceV3;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";

    @BeforeEach
    void setUp() {
        jobsServiceV3 = spy(new JobsServiceV3());
        doReturn(tokenProvider).when(jobsServiceV3).tokenProvider();
        doReturn(cloudFoundryClient).when(jobsServiceV3).cloudFoundryClient(any());
        when(cloudFoundryClient.jobsV3()).thenReturn(jobsV3);
    }

    @Nested
    @DisplayName("get 메서드 테스트")
    class GetMethodTest {

        @Test
        @DisplayName("정상적인 Job ID로 Job 정보를 조회할 수 있다")
        void getWithValidJobId() {
            // Given
            String jobId = "test-job-id";
            GetJobResponse expectedResponse = GetJobResponse.builder()
                    .createdAt(TEST_CREATED_AT)
                    .operation("operation")
                    .id(jobId)
                    .state(JobState.COMPLETE)
                    .build();

            when(jobsV3.get(any(GetJobRequest.class))).thenReturn(Mono.just(expectedResponse));

            // When
            GetJobResponse actualResponse = jobsServiceV3.get(jobId);

            // Then
            assertNotNull(actualResponse);
            assertEquals(jobId, actualResponse.getId());
            assertEquals(JobState.COMPLETE, actualResponse.getState());
            assertEquals(TEST_CREATED_AT, actualResponse.getCreatedAt());
            assertEquals("operation", actualResponse.getOperation());
        }

        @Test
        @DisplayName("존재하지 않는 Job ID로 조회 시 예외가 발생한다")
        void getWithInvalidJobId() {
            // Given
            String invalidJobId = "invalid-job-id";
            when(jobsV3.get(any(GetJobRequest.class))).thenReturn(Mono.error(new IllegalArgumentException("Job not found")));

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> jobsServiceV3.get(invalidJobId));
        }

        @Test
        @DisplayName("Job 조회 시 네트워크 오류가 발생하면 예외가 발생한다")
        void getWithNetworkError() {
            // Given
            String jobId = "test-job-id";
            when(jobsV3.get(any(GetJobRequest.class))).thenReturn(Mono.error(new RuntimeException("Network error")));

            // When & Then
            assertThrows(RuntimeException.class, () -> jobsServiceV3.get(jobId));
        }
    }
}