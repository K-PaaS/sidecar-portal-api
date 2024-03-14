package org.kpaas.sidecar.portal.api.login;

import org.container.platform.api.common.model.Params;
import org.container.platform.api.login.support.PortalGrantedAuthority;
import org.kpaas.sidecar.portal.api.common.RestTemplateService;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("authUtil")
public class AuthUtil extends org.container.platform.api.login.JwtUtil {

    @Autowired
    @Qualifier("sRestTemplateService")
    private RestTemplateService restTemplateService;

    /*public boolean whoami() {
        Whoami whoami = null;
        try {
            Params params = sidecarAuth();

            whoami = restTemplateService.send(TARGET_SIDECAR_API, URI_SIDECAR_API_WHOAMI
                    , HttpMethod.GET, null, Whoami.class, params);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException(MessageConstant.LOGIN_INVALID_CREDENTIALS.getMsg(), ex);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(ex.getHeader(), ex.getClaims(), MessageConstant.LOGIN_TOKEN_EXPIRED.getMsg(), ex);
        }
    }*/

    public Params sidecarAuth() {
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
