package org.kpaas.sidecar.portal.api.login;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.container.platform.api.common.MessageConstant;
import org.container.platform.api.common.model.CommonStatusCode;
import org.container.platform.api.common.model.Params;
import org.kpaas.sidecar.api.exception.SidecarException;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.RestTemplateService;
import org.kpaas.sidecar.portal.api.login.model.Whoami;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.kpaas.sidecar.portal.api.common.Constants.TARGET_SIDECAR_API;
import static org.kpaas.sidecar.portal.api.common.Constants.URI_SIDECAR_API_WHOAMI;

@Component
public class SidecarAuthenticationFilter extends org.container.platform.api.login.CustomJwtAuthenticationFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(org.container.platform.api.common.RestTemplateService.class);

    public SidecarAuthenticationFilter(AuthUtil authUtil) {
        super(authUtil);
    }

    @Autowired
    @Qualifier("sRestTemplateService")
    private RestTemplateService restTemplateService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String uri =  request.getRequestURI();
        if (uri.contains(Constants.URI_SIDECAR_API_PREFIX)) {

            Whoami whoami = null;
            try {
                Params params = ((AuthUtil)super.jwtTokenUtil).sidecarAuth();

                LOGGER.info(String.valueOf(params));
                whoami = restTemplateService.send(TARGET_SIDECAR_API, URI_SIDECAR_API_WHOAMI
                        , HttpMethod.GET, null, Whoami.class, params);

            } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
                throw new BadCredentialsException(MessageConstant.LOGIN_INVALID_CREDENTIALS.getMsg(), ex);
            } catch (ExpiredJwtException ex) {
                throw new ExpiredJwtException(ex.getHeader(), ex.getClaims(), MessageConstant.LOGIN_TOKEN_EXPIRED.getMsg(), ex);
            }

            //boolean validateI = ((AuthUtil) jwtTokenUtil).whoami();
            boolean validateI = whoami.getName().isEmpty();
            if (validateI){
                throw new SidecarException(org.container.platform.api.common.Constants.RESULT_STATUS_FAIL,"failed to get identity", HttpStatus.UNAUTHORIZED.value(), CommonStatusCode.UNAUTHORIZED.getMsg());
            }
        }
        chain.doFilter(request, response);
    }
}
