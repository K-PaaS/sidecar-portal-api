package org.kpaas.sidecar.portal.api.login;


import org.container.platform.api.common.Constants;
import org.container.platform.api.common.MessageConstant;
import org.container.platform.api.common.model.CommonStatusCode;
import org.container.platform.api.common.model.ResultStatus;
import org.container.platform.api.login.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Login Controller 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.09.28
 */
//@Api(value = "LoginController v1")
@RestController("sLoginController")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private org.kpaas.sidecar.portal.api.organizations.OrganizationsService organizationsService;

    @Autowired
    @Qualifier("authUtil")
    private AuthUtil authUtil;

    //@ApiOperation(value = "Org 목록조회(Get Org List)", nickname = "getOrganizationsList")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(name = "params", value = "request parameters", required = true, dataType = "object", paramType = "body")
    //})
    @GetMapping("/sidecar/orgs")
    @ResponseBody
    public Object getOrganizationsList() {

        org.cloudfoundry.client.v3.organizations.Organization org ;
        Object result ;
        try {

            result = organizationsService.getROrganizationsList( authUtil.sidecarAuth() );

        } catch (Exception e) {
            return new ResultStatus(Constants.RESULT_STATUS_FAIL, MessageConstant.LOGIN_FAIL.getMsg(),
                    CommonStatusCode.UNAUTHORIZED.getCode(), e.getMessage());
        }

        return result;
    }

}