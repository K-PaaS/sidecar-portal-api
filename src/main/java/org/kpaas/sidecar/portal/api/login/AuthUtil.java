package org.kpaas.sidecar.portal.api.login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.container.platform.api.accessInfo.AccessTokenService;
import org.container.platform.api.login.support.JWTRoleInfoItem;
import org.container.platform.api.login.support.PortalGrantedAuthority;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.SidecarPropertyService;
import org.kpaas.sidecar.portal.api.common.SidecarResourceYamlService;
import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.login.model.Whoami;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kpaas.sidecar.portal.api.common.Constants.*;

@Service
public class AuthUtil {//extends org.container.platform.api.login.JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthUtil.class);

    @Autowired
    @Qualifier("sidecarRestTemplateService")
    private SidecarRestTemplateService restTemplateService;

    @Autowired
    @Qualifier("sidecarPropertyService")
    private SidecarPropertyService propertyService;

    @Autowired
    private AccessTokenService accessTokenService;

    public Params sidecarAuth() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) authentication.getAuthorities();
        User user = (User) authentication.getPrincipal();

        Params params = new Params();
        params.setUserId(user.getUsername());
        params.setUserAuthId(user.getPassword());
        params.setRs_sa(user.getPassword());

        PortalGrantedAuthority sidecarAuthority = null;
        String clusterId = null;
        List<GrantedAuthority> roles = new ArrayList<>();
        for (GrantedAuthority authority : list) {
            roles.add(authority);
            if (authority instanceof SimpleGrantedAuthority) {
                SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority) authority;
                params.setUserType(simpleGrantedAuthority.getAuthority());

                // SUPER_ADMIN or CLUSTER_ADMIN 인 경우
                if (org.container.platform.api.common.Constants.AUTH_SUPER_ADMIN.equals(simpleGrantedAuthority.getAuthority()) || org.container.platform.api.common.Constants.AUTH_CLUSTER_ADMIN.equals(simpleGrantedAuthority.getAuthority())) {
                    // Do something ?
                }
            }

            if (authority instanceof PortalGrantedAuthority) {
                PortalGrantedAuthority portalAuthority = (PortalGrantedAuthority) authority;

                if (portalAuthority.equals(portalAuthority.getId(), Constants.ContextType.CLUSTER.name())) {
                    if (clusterId == null) {
                        params.setCluster(portalAuthority.getId());
                        params.setIsClusterToken(true);
                        params.setClusterApiUrl(portalAuthority.geturl());
                        params.setClusterToken(portalAuthority.getToken());
                        clusterId = portalAuthority.getId();
                    }
                }

                // assigned sidecar role(k8s)
                if (propertyService.getSidecarRootNamespace().equals(portalAuthority.getId())) {
                    if (sidecarAuthority == null) {
                        params.setClusterApiUrl(portalAuthority.geturl());
                        params.setClusterToken(portalAuthority.getToken());
                        sidecarAuthority = (PortalGrantedAuthority) authority;
                        params.setHasSidecarAuth(true);
                    }
                }
            }
        }
        params.setRoles(roles);

        return params;
    }
    public Params getSidecarRoles() {
        Params params = sidecarAuth();

        if (!Constants.AUTH_USER.equals(params.getUserType())) {
            //if (sidecarAurthority == null) {
            if (!params.hasSidecarAuth) {
                try {// vault/user/7ed3d48f-5edf-46e8-a757-efb23b0e2113/host-cluster/kpaas
                    org.container.platform.api.common.model.Params vaultResult =
                            accessTokenService.getVaultSecrets(new org.container.platform.api.common.model.Params(params.getCluster(), params.getUserAuthId(), AUTH_USER, propertyService.getSidecarRootNamespace()));

                    PortalGrantedAuthority role = new PortalGrantedAuthority(propertyService.getSidecarRootNamespace(), org.container.platform.api.common.Constants.ContextType.NAMESPACE.name(),
                            params.getUserType(), vaultResult.getClusterToken(), vaultResult.getClusterApiUrl(), params.getCluster());

                    params.getRoles().add(role);

                    UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            authentication.getPrincipal(), null, params.getRoles());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    params.setClusterToken(role.getToken());
                } catch (Exception e) {
                    LOGGER.info("*** getVaultSecrets : NOT YET SIDECAR ROLE BINDING...");
                }
            }
        }

        Whoami whoami = whoami(params);
        if (AUTH_SUPER_ADMIN.equals(params.getUserType()) || whoami.getName().contains(params.getUserId()) || whoami.getName().contains(params.getUserAuthId())){
            params.setSidecarStatus(Constants.SidecarStatus.ACTIVE);
        }

        return params;
    }

    public Whoami whoami(Params params){
        return restTemplateService.send(TARGET_SIDECAR_API, URI_SIDECAR_API_WHOAMI
                , HttpMethod.GET, null, Whoami.class, params);
    }
    public Whoami origin_whoami(Params params){
        return restTemplateService.send(TARGET_SIDECAR_API, URI_SIDECAR_API_WHOAMI
                , HttpMethod.GET, null, Whoami.class, params);
    }
    public Whoami origin_sidecarAuth() {
        Params params = new Params();
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) authentication.getAuthorities();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        params.setUserId(user.getUsername());
        params.setUserAuthId(user.getPassword());
        params.setRs_sa(user.getPassword());

        PortalGrantedAuthority sidecarAuthority = null;
        String clusterId = null;
        List<GrantedAuthority> roles = new ArrayList<>();
        for (GrantedAuthority authority : list) {
            roles.add(authority);
            if (authority instanceof SimpleGrantedAuthority) {
                SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority) authority;
                params.setUserType(simpleGrantedAuthority.getAuthority());

                // SUPER_ADMIN or CLUSTER_ADMIN 인 경우
                if (org.container.platform.api.common.Constants.AUTH_SUPER_ADMIN.equals(simpleGrantedAuthority.getAuthority()) || org.container.platform.api.common.Constants.AUTH_CLUSTER_ADMIN.equals(simpleGrantedAuthority.getAuthority())) {
                    // Do something ?
                }
            }

            if (authority instanceof PortalGrantedAuthority) {
                PortalGrantedAuthority portalAuthority = (PortalGrantedAuthority) authority;

                if (portalAuthority.equals(portalAuthority.getId(), Constants.ContextType.CLUSTER.name())) {
                    if (clusterId == null) {
                        params.setCluster(portalAuthority.getId());
                        params.setIsClusterToken(true);
                        params.setClusterApiUrl(portalAuthority.geturl());
                        params.setClusterToken(portalAuthority.getToken());
                        clusterId = portalAuthority.getId();
                    }
                }

                // assigned sidecar role(k8s)
                if (propertyService.getSidecarRootNamespace().equals(portalAuthority.getId())) {
                    if (sidecarAuthority == null) {
                        params.setClusterApiUrl(portalAuthority.geturl());
                        params.setClusterToken(portalAuthority.getToken());
                        sidecarAuthority = (PortalGrantedAuthority) authority;
                    }
                }
            }
        }

        if (!Constants.AUTH_USER.equals(params.getUserType())) {
            if (sidecarAuthority == null) {
                try {// vault/user/7ed3d48f-5edf-46e8-a757-efb23b0e2113/host-cluster/kpaas
                    org.container.platform.api.common.model.Params vaultResult =
                            accessTokenService.getVaultSecrets(new org.container.platform.api.common.model.Params(clusterId, params.getUserAuthId(), AUTH_USER, propertyService.getSidecarRootNamespace()));

                    PortalGrantedAuthority role = new PortalGrantedAuthority(propertyService.getSidecarRootNamespace(), org.container.platform.api.common.Constants.ContextType.NAMESPACE.name(),
                            params.getUserType(), vaultResult.getClusterToken(), vaultResult.getClusterApiUrl(), clusterId);

                    roles.add(role);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            authentication.getPrincipal(), null, roles);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    params.setClusterToken(role.getToken());
                } catch (Exception e) {
                    LOGGER.info("*** getVaultSecrets : NOT YET SIDECAR ROLE BINDING...");
                }
            }
        }

        Whoami whoami = whoami(params);
        if (AUTH_SUPER_ADMIN.equals(params.getUserType()) || whoami.getName().contains(params.getUserId()) || whoami.getName().contains(params.getUserAuthId())){
            params.setSidecarStatus(Constants.SidecarStatus.ACTIVE);
            whoami.setToken(params.getClusterToken());
            whoami.setParams(params);
        }
        return whoami;
    }

    public Params old_sidecarAuth() {
        Params params = new Params();
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) authentication.getAuthorities();

        PortalGrantedAuthority aAuthority = null;
        String clusterId = null;
        for (GrantedAuthority authority : list) {

            if(authority instanceof PortalGrantedAuthority ){
                PortalGrantedAuthority portalAuthority = (PortalGrantedAuthority)authority;

                /*if (portalAuthority.equals(portalAuthority.getId(), Constants.ContextType.CLUSTER.name())){
                    if (clusterId == null) {
                        params.setCluster(portalAuthority.getId());
                        params.setIsClusterToken(true);
                        clusterId = portalAuthority.getId();
                    }
                }*/

                if (Constants.URI_SIDECAR_API_PREFIX.equals(portalAuthority.getId())){
                    if (aAuthority == null) {
                        params.setClusterApiUrl(portalAuthority.geturl());
                        params.setClusterToken(portalAuthority.getToken());
                        aAuthority = (PortalGrantedAuthority)authority;
                    }
                }
            }
        }
        return params;
    }
}
