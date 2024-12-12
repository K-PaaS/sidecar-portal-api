package org.kpaas.sidecar.portal.api.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("sidecarPropertyService")
@Data
@EqualsAndHashCode(callSuper=true)
public class SidecarPropertyService extends org.container.platform.api.common.PropertyService {

    @Value("${sidecarApi.url}")
    private String sidecarApiUrl;

    @Value("${sidecar.rootNamespace}")
    private String sidecarRootNamespace;

    @Value("${sidecar.roles.admin}")
    private String sidecarRolesAdmin;

    @Value("${cpPortal.adminName}")
    private String cpPortalAdminName;
}
