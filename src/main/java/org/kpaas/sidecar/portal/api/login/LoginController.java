package org.kpaas.sidecar.portal.api.login;


import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.container.platform.api.common.Constants;
import org.container.platform.api.common.MessageConstant;
import org.container.platform.api.common.model.CommonStatusCode;
import org.container.platform.api.common.model.ResultStatus;
import org.container.platform.api.users.Users;

import org.kpaas.sidecar.portal.api.common.SidecarPropertyService;
import org.kpaas.sidecar.portal.api.common.SidecarResourceYamlService;
import org.kpaas.sidecar.portal.api.common.model.Params;
import org.kpaas.sidecar.portal.api.service.OrganizationsServiceV3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import static org.container.platform.api.common.Constants.AUTH_USER;


/**
 * Login Controller 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.09.28
 */
@Api(value = "LoginController v1")
@RestController("sLoginController")
public class LoginController {

    @Autowired
    private OrganizationsServiceV3 organizationsServiceV3;

    @Autowired
    @Qualifier("sidecarPropertyService")
    private SidecarPropertyService propertyService;

    @Autowired
    private SidecarResourceYamlService resourceYamlService;

    @Autowired
    private org.kpaas.sidecar.portal.api.organizations.OrganizationsService organizationsService;

    @Autowired
    @Qualifier("authUtil")
    private AuthUtil authUtil;

    @ApiOperation(value = "Org 목록조회(Get Org List)", nickname = "getOrganizationsList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "request parameters", required = true, dataType = "object", paramType = "body")
    })
    @GetMapping("/sidecar/orgs")
    @ResponseBody
    public Object getOrganizationsList() {

        org.cloudfoundry.client.v3.organizations.Organization org ;
        Object result ;
        try {

            result = organizationsService.getOrganizationsList( authUtil.sidecarAuth() );

        } catch (Exception e) {
            return new ResultStatus(Constants.RESULT_STATUS_FAIL, MessageConstant.LOGIN_FAIL.getMsg(),
                    CommonStatusCode.UNAUTHORIZED.getCode(), e.getMessage());
        }

        return result;
    }

    @PostMapping("/sidecar/rolebindings")
    @ResponseBody
    public Object roleBindingsAdmin(@RequestBody Users user) {
        //SUPER_ADMIN에게 sidecar 최초 허용
        Params authParams =  authUtil.sidecarAuth();

        if (AUTH_USER.equals(authParams.getUserType())){
            return new ResultStatus(Constants.RESULT_STATUS_FAIL, MessageConstant.INVALID_AUTHORITY.getMsg(),
                    CommonStatusCode.FORBIDDEN.getCode(), MessageConstant.INVALID_AUTHORITY.getMsg());
        }

        //request body check
        Assert.hasText(user.userId);
        Assert.hasText(user.userAuthId);
        Assert.hasText(user.userType);
        Assert.hasText(user.clusterId);
        Assert.hasText(user.roleSetCode); //admin(korifi-ctrl-admin) / user(korifi-ctrl-root-ns-user)

        //user attributes setting
        user.setCpNamespace(propertyService.getSidecarRootNamespace());
        user.setServiceAccountName(user.getUserAuthId());
        user.setClusterId(user.getClusterId());

        //params attributes setting
        Params params = new Params();
        params.setUserId(user.userId);
        params.setUserType(user.userType);
        params.setUserAuthId(user.serviceAccountName);
        params.setRs_sa(user.serviceAccountName);
        params.setRs_role(user.roleSetCode);
        params.setNamespace(user.cpNamespace);
        params.setIsClusterToken(true);
        params.setCluster(user.getClusterId());
        params.setClusterToken(authParams.getClusterToken());

        resourceYamlService.createSidecarResource(params, user);

        return true;
    }
}
