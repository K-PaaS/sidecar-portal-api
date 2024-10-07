package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.tasks.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Task Service 클래스 테스트
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
public class TasksServiceTest {

    @Mock
    private ReactorCloudFoundryClient cloudFoundryClient;

    @Mock
    private Tasks tasks;

    @Mock
    private TokenGrantTokenProvider tokenProvider;

    @Spy
    @InjectMocks
    private TasksService tasksService;

    @BeforeEach
    void setUp() {
        doReturn(tasks).when(cloudFoundryClient).tasks();
        doReturn(tokenProvider).when(tasksService).tokenProvider();
        doReturn(cloudFoundryClient).when(tasksService).cloudFoundryClient(any());
    }

    @Test
    @DisplayName("Task Cancel 기능 테스트")
    void cancel() {
        String guid = "test-guid";
        CancelTaskResponse mockResponse = CancelTaskResponse.builder()
                .id(guid)
                .state(TaskState.CANCELING)
                .createdAt("2023-01-01T00:00:00Z")
                .diskInMb(5)
                .dropletId("droplet-id")
                .memoryInMb(1024)
                .name("test-guid")
                .sequenceId(5)
                .build();

        when(tasks.cancel(any(CancelTaskRequest.class))).thenReturn(Mono.just(mockResponse));

        CancelTaskResponse response = tasksService.cancel(guid);

        assertNotNull(response);
        assertEquals(guid, response.getId());
        assertEquals(TaskState.CANCELING, response.getState());
        verify(tasks).cancel(argThat(request ->
                request.getTaskId().equals(guid)
        ));
    }

    @Test
    @DisplayName("Task Create 기능 테스트")
    void create() {
        String guid = "test-guid";
        CreateTaskResponse mockResponse = CreateTaskResponse.builder()
                .id(guid)
                .state(TaskState.PENDING)
                .createdAt("2023-01-01T00:00:00Z")
                .diskInMb(5)
                .dropletId("droplet-id")
                .memoryInMb(1024)
                .name("test-guid")
                .sequenceId(5)
                .build();

        when(tasks.create(any(CreateTaskRequest.class))).thenReturn(Mono.just(mockResponse));

        CreateTaskResponse response = tasksService.create(guid);

        assertNotNull(response);
        assertEquals(guid, response.getId());
        assertEquals(TaskState.PENDING, response.getState());
        verify(tasks).create(argThat(request ->
                request.getApplicationId().equals(guid)
        ));
    }

    @Test
    @DisplayName("특정 Task 조회 기능 테스트")
    void get() {
        String guid = "test-guid";
        GetTaskResponse mockResponse = GetTaskResponse.builder()
                .id(guid)
                .state(TaskState.RUNNING)
                .createdAt("2023-01-01T00:00:00Z")
                .diskInMb(5)
                .dropletId("droplet-id")
                .memoryInMb(1024)
                .name("test-guid")
                .sequenceId(5)
                .build();

        when(tasks.get(any(GetTaskRequest.class))).thenReturn(Mono.just(mockResponse));

        GetTaskResponse response = tasksService.get(guid);

        assertNotNull(response);
        assertEquals(guid, response.getId());
        assertEquals(TaskState.RUNNING, response.getState());
        verify(tasks).get(argThat(request ->
                request.getTaskId().equals(guid)
        ));
    }

    @Test
    @DisplayName("Task 목록 조회 기능 테스트")
    void list() {
        List<String> appGuids = Arrays.asList("app1", "app2");
        List<String> orgGuids = Arrays.asList("org1", "org2");
        List<String> spaceGuids = Arrays.asList("space1", "space2");

        ListTasksResponse mockResponse = ListTasksResponse.builder()
                .addAllResources(Arrays.asList(
                        TaskResource.builder()
                                .id("task1")
                                .state(TaskState.RUNNING)
                                .createdAt("2023-01-01T00:00:00Z")
                                .diskInMb(5)
                                .dropletId("droplet-id")
                                .memoryInMb(1024)
                                .name("test-guid")
                                .sequenceId(5)
                                .build(),
                        TaskResource.builder()
                                .id("task2")
                                .state(TaskState.SUCCEEDED)
                                .createdAt("2023-01-01T00:00:00Z")
                                .diskInMb(5)
                                .dropletId("droplet-id")
                                .memoryInMb(1024)
                                .name("test-guid")
                                .sequenceId(5)
                                .build()
                ))
                .build();

        when(tasks.list(any(ListTasksRequest.class))).thenReturn(Mono.just(mockResponse));

        ListTasksResponse response = tasksService.list(appGuids, orgGuids, spaceGuids);

        assertNotNull(response);
        assertEquals(2, response.getResources().size());
        assertEquals("task1", response.getResources().get(0).getId());
        assertEquals(TaskState.RUNNING, response.getResources().get(0).getState());
        verify(tasks).list(argThat(request ->
                request.getApplicationIds().containsAll(appGuids) &&
                        request.getOrganizationIds().containsAll(orgGuids) &&
                        request.getSpaceIds().containsAll(spaceGuids)
        ));
    }
}