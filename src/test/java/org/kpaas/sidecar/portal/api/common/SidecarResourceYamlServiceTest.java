//package org.kpaas.sidecar.portal.api.common;
//
//import org.container.platform.api.accessInfo.AccessToken;
//import org.container.platform.api.common.Constants;
//import org.container.platform.api.common.ResourceYamlService;
//import org.container.platform.api.common.TemplateService;
//import org.container.platform.api.common.VaultService;
//import org.container.platform.api.common.model.CommonStatusCode;
//import org.container.platform.api.common.model.ResultStatus;
//import org.container.platform.api.exception.ResultStatusException;
//import org.container.platform.api.users.Users;
//import org.container.platform.api.users.UsersList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpMethod;
//import org.kpaas.sidecar.portal.api.common.model.Params;
//
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class SidecarResourceYamlServiceTest {
//
//    @Mock
//    private SidecarPropertyService propertyService;
//    @Mock
//    private TemplateService templateService;
//    @Mock
//    private SidecarRestTemplateService restTemplateService;
//    @Mock
//    private ResourceYamlService resourceYamlService;
//    @Mock
//    private VaultService vaultService;
//
//    @InjectMocks
//    private SidecarResourceYamlService sidecarResourceYamlService;
//
//    private Params params;
//    private Users user;
//
//    @BeforeEach
//    void setUp() {
//        params = new Params();
//        params.setRs_sa("test-sa");
//        params.setRs_role("test-role");
//        params.setNamespace("test-namespace");
//        params.setUserAuthId("test-auth-id");
//        params.setCluster("test-cluster");
//
//
//        user = new Users();
//        user.setClusterId("test-cluster");
//        user.setCpNamespace("test-namespace");
//        user.setUserId("test-user");
//        user.setUserAuthId("test-auth-id");
//        user.setUserType("test-type");
//        user.setRoleSetCode("test-role-set");
//        user.setServiceAccountName("test-sa");
//        user.setSaSecret("test-secret");
//    }
//
//    @Test
//    void createSidecarResource_Success() throws Exception {
//        when(resourceYamlService.createServiceAccount(params)).thenReturn(new ResultStatus());
//        when(resourceYamlService.createSecret(params)).thenReturn(new ResultStatus());
//        AccessToken mockAccessToken = mock(AccessToken.class);
//        when(mockAccessToken.getUserAccessToken()).thenReturn("test-token");
//        when(resourceYamlService.getSecrets(params)).thenReturn(mockAccessToken);
//        when(propertyService.getSidecarRootNamespace()).thenReturn("root-namespace");
//
//        UsersList mockUsersList = new UsersList();
//        mockUsersList.setItems(Collections.singletonList(user));
//        when(restTemplateService.send(anyString(), anyString(), eq(HttpMethod.GET), isNull(), eq(UsersList.class), eq(params))).thenReturn(mockUsersList);
//
//        when(propertyService.getSidecarRolesAdmin()).thenReturn("admin-role");
//        when(templateService.convert(anyString(), anyMap())).thenReturn("yaml-content");
//        when(restTemplateService.sendYaml(anyString(), anyString(), eq(HttpMethod.POST), eq(ResultStatus.class), eq(params))).thenReturn(new ResultStatus());
//
//        sidecarResourceYamlService.createSidecarResource(params, user);
//
//        verify(resourceYamlService).createServiceAccount(params);
//        verify(resourceYamlService).createSecret(params);
//        verify(vaultService).saveUserAccessToken(params);
//        verify(resourceYamlService).createUsers(any(Users.class));
//    }
//
//    @Test
//    void createSidecarResource_ServiceAccountAlreadyExists() throws Exception {
//        when(resourceYamlService.createServiceAccount(params))
//                .thenThrow(new ResultStatusException(String.valueOf(CommonStatusCode.CONFLICT.getCode())));
//
//        when(resourceYamlService.createSecret(params)).thenReturn(new ResultStatus());
//        AccessToken mockAccessToken = mock(AccessToken.class);
//        when(mockAccessToken.getUserAccessToken()).thenReturn("test-token");
//        when(resourceYamlService.getSecrets(params)).thenReturn(mockAccessToken);
//
//        UsersList mockUsersList = new UsersList();
//        mockUsersList.setItems(Collections.singletonList(user));
//        when(restTemplateService.send(anyString(), anyString(), eq(HttpMethod.GET), isNull(), eq(UsersList.class), eq(params))).thenReturn(mockUsersList);
//
//        when(propertyService.getSidecarRolesAdmin()).thenReturn("admin-role");
//        when(templateService.convert(anyString(), anyMap())).thenReturn("yaml-content");
//        when(restTemplateService.sendYaml(anyString(), anyString(), eq(HttpMethod.POST), eq(ResultStatus.class), eq(params))).thenReturn(new ResultStatus());
//
//        assertDoesNotThrow(() -> sidecarResourceYamlService.createSidecarResource(params, user));
//    }
//
//    @Test
//    void getUsersById_Success() {
//        UsersList expectedUsersList = new UsersList();
//        when(restTemplateService.send(anyString(), anyString(), eq(HttpMethod.GET),
//                isNull(), eq(UsersList.class), eq(params)))
//                .thenReturn(expectedUsersList);
//
//        UsersList result = sidecarResourceYamlService.getUsersById(params);
//
//        assertEquals(expectedUsersList, result);
//    }
//
//    @Test
//    void createClusterRoleBinding_Success() {
//        ResultStatus expectedStatus = new ResultStatus();
//        when(templateService.convert(anyString(), anyMap())).thenReturn("yaml-content");
//        when(restTemplateService.sendYaml(anyString(), anyString(),
//                eq(HttpMethod.POST), eq(ResultStatus.class), eq(params)))
//                .thenReturn(expectedStatus);
//
//        ResultStatus result = sidecarResourceYamlService.createClusterRoleBinding(params);
//
//        assertEquals(expectedStatus, result);
//    }
//
//    @Test
//    void deleteRoleBinding_Success() {
//        ResultStatus expectedStatus = new ResultStatus();
//        when(restTemplateService.send(anyString(), anyString(),
//                eq(HttpMethod.DELETE), isNull(), eq(ResultStatus.class), eq(params)))
//                .thenReturn(expectedStatus);
//
//        ResultStatus result = sidecarResourceYamlService.deleteRoleBinding(params, "test-rb");
//
//        assertEquals(expectedStatus, result);
//    }
//
//    @Test
//    void deleteUsers_Success() {
//        ResultStatus expectedStatus = new ResultStatus();
//        when(restTemplateService.send(anyString(), anyString(), eq(HttpMethod.DELETE),
//                isNull(), eq(ResultStatus.class), eq(params)))
//                .thenReturn(expectedStatus);
//
//        ResultStatus result = sidecarResourceYamlService.deleteUsers(params);
//
//        assertEquals(expectedStatus, result);
//    }
//
//    @Test
//    void updateSidecarResource_Success() {
//        when(propertyService.getSidecarRolesAdmin()).thenReturn("admin-role");
//        UsersList mockUsersList = new UsersList();
//        mockUsersList.setItems(Collections.singletonList(user));
//        when(restTemplateService.send(anyString(), anyString(), eq(HttpMethod.GET), isNull(), eq(UsersList.class), eq(params))).thenReturn(mockUsersList);
//        when(resourceYamlService.createUsers(any(Users.class))).thenReturn(new ResultStatus());
//
//        assertDoesNotThrow(() -> sidecarResourceYamlService.updateSidecarResource(params, user));
//    }
//
//    @Test
//    void deleteSidecarResource_Success() {
//        params.setRs_role(Constants.NOT_ASSIGNED_ROLE);
//        when(resourceYamlService.deleteServiceAccount(params)).thenReturn(new ResultStatus());
//        doNothing().when(vaultService).deleteUserAccessToken(params);
//
//        assertDoesNotThrow(() -> sidecarResourceYamlService.deleteSidecarResource(params, user));
//    }
//}