package org.kpaas.sidecar.portal.api.login;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.container.platform.api.common.MessageConstant;
import org.container.platform.api.common.model.CommonStatusCode;
import org.container.platform.api.login.JwtUtil;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.api.exception.SidecarException;
import org.kpaas.sidecar.portal.api.login.model.Whoami;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SidecarAuthenticationFilter extends OncePerRequestFilter {//extends org.container.platform.api.login.CustomJwtAuthenticationFilter{

    private AuthUtil authUtil;
    public SidecarAuthenticationFilter(AuthUtil authUtil) {
        //super(authUtil);
        this.authUtil = authUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (!request.getRequestURI().contains(Constants.URI_LOGIN) && !request.getRequestURI().contains(Constants.URI_SIGN_UP)){
      //if (request.getRequestURI().contains(Constants.URI_SIDECAR_API_PREFIX)) {
            try {
                Params params = authUtil.getSidecarRoles();

                if (Constants.AUTH_USER.equals(params.getUserType())
                        && Constants.SidecarStatus.ACTIVE != params.getSidecarStatus()){
                    throw new SidecarException(org.container.platform.api.common.Constants.RESULT_STATUS_FAIL,"failed to get identity", HttpStatus.UNAUTHORIZED.value(), CommonStatusCode.UNAUTHORIZED.getMsg());
                }
            } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
                throw new BadCredentialsException(MessageConstant.LOGIN_INVALID_CREDENTIALS.getMsg(), ex);
            } catch (ExpiredJwtException ex) {
                throw new ExpiredJwtException(ex.getHeader(), ex.getClaims(), MessageConstant.LOGIN_TOKEN_EXPIRED.getMsg(), ex);
            }
        }
        chain.doFilter(request, response);
    }
}
