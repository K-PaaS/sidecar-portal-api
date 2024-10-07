package org.kpaas.sidecar.portal.api.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *  SidecarProperty Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("SidecarPropertyService 테스트")
public class SidecarPropertyServiceTest {

    @InjectMocks
    private SidecarPropertyService sidecarPropertyService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(sidecarPropertyService, "sidecarApiUrl", "http://test-sidecar-api.com");
        ReflectionTestUtils.setField(sidecarPropertyService, "sidecarRootNamespace", "test-namespace");
        ReflectionTestUtils.setField(sidecarPropertyService, "sidecarRolesAdmin", "test-admin-role");
    }

    @Test
    @DisplayName("SidecarPropertyService 초기화 확인")
    public void sidecarPropertyServiceInitialization() {
        assertNotNull(sidecarPropertyService, "SidecarPropertyService should be initialized");
    }

    @Test
    @DisplayName("sidecarApiUrl 속성 값 확인")
    public void sidecarApiUrl() {
        assertEquals("http://test-sidecar-api.com", sidecarPropertyService.getSidecarApiUrl(),
                "SidecarApiUrl should match the test value");
    }

    @Test
    @DisplayName("sidecarRootNamespace 속성 값 확인")
    public void sidecarRootNamespace() {
        assertEquals("test-namespace", sidecarPropertyService.getSidecarRootNamespace(),
                "SidecarRootNamespace should match the test value");
    }

    @Test
    @DisplayName("sidecarRolesAdmin 속성 값 확인")
    public void sidecarRolesAdmin() {
        assertEquals("test-admin-role", sidecarPropertyService.getSidecarRolesAdmin(),
                "SidecarRolesAdmin should match the test value");
    }
}