package org.kpaas.sidecar.portal.api.common;

import org.container.platform.api.accessInfo.AccessToken;
import org.container.platform.api.common.ResourceYamlService;
import org.container.platform.api.common.TemplateService;
import org.container.platform.api.common.VaultService;
import org.container.platform.api.common.model.CommonStatusCode;
import org.container.platform.api.common.model.ResultStatus;
import org.container.platform.api.exception.ResultStatusException;
import org.container.platform.api.users.Users;

import org.kpaas.sidecar.portal.api.common.model.Params;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.kpaas.sidecar.portal.api.common.Constants.*;


/**
 * Resource Yaml Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.14
 **/
@Service
public class SidecarResourceYamlService {//extends org.container.platform.api.common.ResourceYamlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SidecarResourceYamlService.class);

    private final SidecarPropertyService  propertyService;
    private final TemplateService templateService;
    private final SidecarRestTemplateService restTemplateService;
    private final ResourceYamlService resourceYamlService;
    private final VaultService vaultService;

    @Autowired
    public SidecarResourceYamlService(
                               @Qualifier("sidecarPropertyService") SidecarPropertyService propertyService,
                               TemplateService templateService,
                               @Qualifier("sidecarRestTemplateService") SidecarRestTemplateService restTemplateService,
                               ResourceYamlService resourceYamlService,
                               VaultService vaultService) {

        this.propertyService = propertyService;
        this.templateService = templateService;
        this.restTemplateService = restTemplateService;
        this.resourceYamlService = resourceYamlService;
        this.vaultService = vaultService;
    }

    public void createSidecarResource(Params params, Users user) {

        try {
            // service-account  생성 in root_namespace
            resourceYamlService.createServiceAccount(params);
        } catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.CONFLICT.getCode()) {
                LOGGER.info("*** CREATE_SIDECAR_RESOURCE: SERVICE ACCOUNT ALREADY EXISTS WITH THAT NAME...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }

        try {
            // if user,  role-binding 생성 "korifi-controllers-root-namespace-user"
            // if admin, role-binding 생성 "korifi-controllers-admin"
            createClusterRoleBinding(params);
        } catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.CONFLICT.getCode()) {
                LOGGER.info("*** CREATE_SIDECAR_RESOURCE: ROLE BINDING ALREADY EXISTS WITH THAT NAME...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }

        try {
            // token 생성
            //params.setIsClusterToken(true);
            resourceYamlService.createSecret(params);
        } catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.CONFLICT.getCode()) {
                LOGGER.info("*** CREATE_SIDECAR_RESOURCE: SECRET ALREADY EXISTS WITH THAT NAME...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }

        // Secret.Token 가져오기
        AccessToken saToken = resourceYamlService.getSecrets(params);
        params.setSaToken(saToken.getUserAccessToken());

        // vault-user-token 등록
        //params.setUserType(AUTH_SUPER_ADMIN); // if AUTH_SUPER_ADMIN, vault path에서 cluster
        params.setUserType(AUTH_USER); // if AUTH_USER, vault path에서 cluster
        vaultService.saveUserAccessToken(params);

        Users newUser = new Users(user.getClusterId(), user.getCpNamespace(), user.getUserId(), user.getUserAuthId(),
                user.getUserType(), user.getRoleSetCode(), user.getServiceAccountName(), user.getSaSecret());

        // db 사용자 정보 등록
        resourceYamlService.createUsers(newUser);
    }

    public ResultStatus createClusterRoleBinding(org.container.platform.api.common.model.Params params) {
        Map map = new HashMap();

        map.put("userName", params.getRs_sa());
        map.put("roleName", params.getRs_role());
        map.put("nsName", params.getNamespace());
        map.put("roleGuid", UUID.randomUUID().toString());

        //if (AUTH_USER.equals(params.getUserType())){
        if (!propertyService.getSidecarRolesAdmin().equals(params.getRs_role())){
            map.put("rbName", Constants.CFUSER_ROLEBINDING_NAME.replace("{username}", params.getRs_sa()) );
            params.setYaml(templateService.convert("sidecar/create_userRoleBinding.ftl", map));
        }else {
            map.put("rbName", Constants.CFADMIN_ROLEBINDING_NAME.replace("{username}", params.getRs_sa()) );
            params.setYaml(templateService.convert("sidecar/create_adminRoleBinding.ftl", map));
        }

        ResultStatus resultStatus = restTemplateService.sendYaml(Constants.TARGET_CP_MASTER_API,
                propertyService.getCpMasterApiListRoleBindingsCreateUrl(), HttpMethod.POST, ResultStatus.class, params);

        //return (ResultStatus) commonService.setResultModel(resultStatus, Constants.RESULT_STATUS_SUCCESS);
        return resultStatus;
    }
}
