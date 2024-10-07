package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.processes.*;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.model.Process;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *  Processes Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessesService 테스트")
class ProcessesServiceTest {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private Processes processes;

    @InjectMocks
    private ProcessesService processesService;

    private static final String TEST_CREATED_AT = "2023-01-01T00:00:00Z";
    private static final String TEST_ID = "service-instance-id";

    @BeforeEach
    void setUp() {
        processesService = new ProcessesService() {
            @Override
            public TokenProvider tokenProvider() {
                return mock(TokenProvider.class);
            }

            @Override
            public ReactorCloudFoundryClient cloudFoundryClient(TokenProvider tokenProvider) {
                return cloudFoundryClient;
            }
        };
        when(cloudFoundryClient.processes()).thenReturn(processes);
    }

    @Test
    @DisplayName("프로세스 정보 조회 테스트")
    void get() {
        String guid = "test-guid";
        GetProcessResponse expectedResponse = GetProcessResponse.builder()
                .memoryInMb(5)
                .metadata(Metadata.builder().build())
                .relationships(ProcessRelationships.builder().build())
                .type("")
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .command("")
                .diskInMb(2048)
                .healthCheck(HealthCheck.builder()
                        .type(HealthCheckType.HTTP)
                        .build())
                .instances(3)
                .build();
        when(processes.get(any(GetProcessRequest.class))).thenReturn(Mono.just(expectedResponse));

        GetProcessResponse actualResponse = processesService.get(guid);

        assertEquals(expectedResponse, actualResponse);
        verify(processes).get(argThat(request -> request.getProcessId().equals(guid)));
    }

    @Test
    @DisplayName("프로세스 통계 정보 조회 테스트")
    void getStatistics() {
        String guid = "test-guid";
        GetProcessStatisticsResponse expectedResponse = GetProcessStatisticsResponse.builder().build();
        when(processes.getStatistics(any(GetProcessStatisticsRequest.class))).thenReturn(Mono.just(expectedResponse));

        GetProcessStatisticsResponse actualResponse = processesService.getStatistics(guid);

        assertEquals(expectedResponse, actualResponse);
        verify(processes).getStatistics(argThat(request -> request.getProcessId().equals(guid)));
    }

    @Test
    @DisplayName("프로세스 목록 조회 테스트")
    void list() {
        List<String> appGuids = Arrays.asList("app1", "app2");
        List<String> orgGuids = Arrays.asList("org1", "org2");
        List<String> spaceGuids = Arrays.asList("space1", "space2");
        ListProcessesResponse expectedResponse = ListProcessesResponse.builder().build();
        when(processes.list(any(ListProcessesRequest.class))).thenReturn(Mono.just(expectedResponse));

        ListProcessesResponse actualResponse = processesService.list(appGuids, orgGuids, spaceGuids);

        assertEquals(expectedResponse, actualResponse);
        verify(processes).list(argThat(request ->
                request.getApplicationIds().equals(appGuids) &&
                        request.getOrganizationIds().equals(orgGuids) &&
                        request.getSpaceIds().equals(spaceGuids)
        ));
    }

    @Test
    @DisplayName("프로세스 스케일 조정 테스트")
    void scale() {
        String processGuid = "process-guid";
        Process process = new Process();
        process.setInstances(3);
        process.setMemoryInMb(1024);
        process.setDiskInMb(2048);
        ScaleProcessResponse expectedResponse = ScaleProcessResponse.builder()
                .memoryInMb(process.getMemoryInMb())
                .metadata(Metadata.builder().build())
                .relationships(ProcessRelationships.builder().build())
                .type("")
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .command("")
                .diskInMb(process.getDiskInMb())
                .healthCheck(HealthCheck.builder()
                        .type(HealthCheckType.HTTP)
                        .build())
                .instances(process.getInstances())
                .build();
        when(processes.scale(any(ScaleProcessRequest.class))).thenReturn(Mono.just(expectedResponse));

        ScaleProcessResponse actualResponse = processesService.scale(processGuid, process);

        assertEquals(expectedResponse, actualResponse);
        verify(processes).scale(argThat(request ->
                request.getProcessId().equals(processGuid) &&
                        request.getInstances() == process.getInstances() &&
                        request.getMemoryInMb() == process.getMemoryInMb() &&
                        request.getDiskInMb() == process.getDiskInMb()
        ));
    }

    @Test
    @DisplayName("커스텀 TokenProvider를 사용한 프로세스 스케일 조정 테스트")
    void scaleWithTokenProvider() {
        String processGuid = "process-guid";
        Process process = new Process();
        process.setInstances(3);
        process.setMemoryInMb(1024);
        process.setDiskInMb(2048);
        TokenProvider customTokenProvider = mock(TokenProvider.class);
        ScaleProcessResponse expectedResponse = ScaleProcessResponse.builder()
                .memoryInMb(process.getMemoryInMb())
                .metadata(Metadata.builder().build())
                .relationships(ProcessRelationships.builder().build())
                .type("")
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .command("")
                .diskInMb(process.getDiskInMb())
                .healthCheck(HealthCheck.builder()
                        .type(HealthCheckType.HTTP)
                        .build())
                .instances(process.getInstances())
                .build();
        when(processes.scale(any(ScaleProcessRequest.class))).thenReturn(Mono.just(expectedResponse));

        ScaleProcessResponse actualResponse = processesService.scale(processGuid, process, customTokenProvider);

        assertEquals(expectedResponse, actualResponse);
        verify(processes).scale(argThat(request ->
                request.getProcessId().equals(processGuid) &&
                        request.getInstances() == process.getInstances() &&
                        request.getMemoryInMb() == process.getMemoryInMb() &&
                        request.getDiskInMb() == process.getDiskInMb()
        ));
    }

    @Test
    @DisplayName("프로세스 인스턴스 종료 테스트")
    void terminateInstance() {
        String guid = "test-guid";
        when(processes.terminateInstance(any(TerminateProcessInstanceRequest.class))).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> processesService.terminateInstance(guid));
        verify(processes).terminateInstance(any(TerminateProcessInstanceRequest.class));
    }

    @Test
    @DisplayName("프로세스 정보 업데이트 테스트")
    void update() {
        String processGuid = "process-guid";
        Process process = new Process();
        process.setHealthCheck(HealthCheck.builder()
                .type(HealthCheckType.HTTP)
                .build());
        process.setCommand("command");
        UpdateProcessResponse expectedResponse = UpdateProcessResponse.builder()
                .memoryInMb(5)
                .metadata(Metadata.builder().build())
                .relationships(ProcessRelationships.builder().build())
                .type("")
                .createdAt(TEST_CREATED_AT)
                .id(TEST_ID)
                .command(process.getCommand())
                .diskInMb(2048)
                .healthCheck(process.getHealthCheck())
                .instances(5)
                .build();
        when(processes.update(any(UpdateProcessRequest.class))).thenReturn(Mono.just(expectedResponse));

        UpdateProcessResponse actualResponse = processesService.update(processGuid, process);

        assertEquals(expectedResponse, actualResponse);
        verify(processes).update(argThat(request ->
                request.getProcessId().equals(processGuid) &&
                        request.getHealthCheck().equals(process.getHealthCheck()) &&
                        request.getCommand().equals(process.getCommand())
        ));
    }
}