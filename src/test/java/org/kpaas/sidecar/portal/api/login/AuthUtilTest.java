package org.kpaas.sidecar.portal.api.login;

import org.container.platform.api.accessInfo.AccessTokenService;
import org.container.platform.api.common.model.Params;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.SidecarPropertyService;
import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
import org.kpaas.sidecar.portal.api.login.model.Whoami;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 *  AuthUtil 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthUtil 클래스 테스트")
class AuthUtilTest {

    @InjectMocks
    private AuthUtil authUtil;

    @Mock
    private SidecarRestTemplateService restTemplateService;

    @Mock
    private SidecarPropertyService propertyService;

    @Mock
    private AccessTokenService accessTokenService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("sidecarAuth 메서드: SUPER_ADMIN 권한으로 인증 테스트")
    void sidecarAuth_WithSuperAdminAuth() {
        // Given
        User user = new User("testUser", "password", Collections.singletonList(new SimpleGrantedAuthority(Constants.AUTH_SUPER_ADMIN)));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // When
        Params result = authUtil.sidecarAuth();

        // Then
        assertNotNull(result);
        assertEquals("testUser", result.getUserId());
        assertEquals(Constants.AUTH_SUPER_ADMIN, result.getUserType());
    }

    @Test
    @DisplayName("getSidecarRoles 메서드: 일반 사용자 권한으로 Sidecar 역할 획득 테스트")
    void getSidecarRoles_WithUserAuth() {
        // Given
        User user = new User("testUser", "password", Collections.singletonList(new SimpleGrantedAuthority(Constants.AUTH_USER)));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        lenient().when(propertyService.getSidecarRootNamespace()).thenReturn("sidecar-namespace");

        Params vaultResult = new Params();
        vaultResult.setClusterToken("test-token");
        vaultResult.setClusterApiUrl("test-url");
        lenient().when(accessTokenService.getVaultSecrets(any())).thenReturn(vaultResult);

        Whoami mockWhoami = new Whoami();
        mockWhoami.setName("testUser");
        lenient().when(restTemplateService.send(eq(Constants.TARGET_SIDECAR_API), eq(Constants.URI_SIDECAR_API_WHOAMI),
                        any(), isNull(), eq(Whoami.class), any(Params.class)))
                .thenReturn(mockWhoami);

        // When
        Params result = authUtil.getSidecarRoles();

        // Then
        assertNotNull(result);
        assertEquals("testUser", result.getUserId());
        assertEquals(Constants.AUTH_USER, result.getUserType());

        // Verify that whoami was called
        verify(restTemplateService, times(1)).send(
                eq(Constants.TARGET_SIDECAR_API),
                eq(Constants.URI_SIDECAR_API_WHOAMI),
                any(),
                isNull(),
                eq(Whoami.class),
                any(Params.class)
        );
    }
}