package org.kpaas.sidecar.portal.api.common;

import org.container.platform.api.accessInfo.AccessToken;
import org.container.platform.api.common.CommonService;
import org.container.platform.api.common.ResourceYamlService;
import org.container.platform.api.common.TemplateService;
import org.container.platform.api.common.VaultService;
import org.container.platform.api.common.model.CommonStatusCode;
import org.container.platform.api.common.model.ResultStatus;
import org.container.platform.api.exception.ResultStatusException;
import org.container.platform.api.users.Users;

import org.container.platform.api.users.UsersList;
import org.kpaas.sidecar.portal.api.common.model.Params;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        //super(commonService, propertyService, templateService, restTemplateService, resourceQuotasService, vaultService);
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
        AccessToken saToken = null;
        try {
            saToken = resourceYamlService.getSecrets(params);
        } catch (NullPointerException e) {
            LOGGER.info("*** CREATE_SIDECAR_RESOURCE: GET SECRET NullPointerException... retry");
            // 생성 후 시간차 retry
            saToken = resourceYamlService.getSecrets(params);
        }

        // vault-user-token 등록
        //params.setUserType(AUTH_SUPER_ADMIN); // if AUTH_SUPER_ADMIN, vault path에서 cluster
        params.setSaToken(saToken.getUserAccessToken());
        params.setUserType(AUTH_USER); // if AUTH_USER, vault path에서 cluster
        vaultService.saveUserAccessToken(params);

        Users newUser = new Users(user.getClusterId(), user.getCpNamespace(), user.getUserId(), user.getUserAuthId(),
                user.getUserType(), user.getRoleSetCode(), user.getServiceAccountName(), user.getSaSecret());

        // db 사용자&롤 정보 조회
        boolean hasAleadyRole = false;
        List<Users> usersList = getUsersById(params).getItems();
        for(Users x : usersList) {
            if (propertyService.getSidecarRootNamespace().equals(x.getCpNamespace())
                    && newUser.getRoleSetCode().equals(x.getRoleSetCode())
                    && newUser.getClusterId().equals(x.getClusterId())) {
                hasAleadyRole = true;
            }
        }

        // db 사용자 정보 등록
        if(!hasAleadyRole)
            resourceYamlService.createUsers(newUser);
    }
    public UsersList getUsersById(Params params) {
        return restTemplateService.send(TARGET_COMMON_API,
                org.container.platform.api.common.Constants.URI_COMMON_API_USERS_DETAIL
                        .replace("{userId:.+}", params.getUserId())
                        .replace("{userAuthId:.+}", params.getUserAuthId()), HttpMethod.GET, null, UsersList.class, params);
    }
    public ResultStatus createClusterRoleBinding(Params params) {
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

        return resultStatus;
    }

    public ResultStatus deleteRoleBinding(Params params, String rbname) {
        ResultStatus resultStatus = null;
        try {
            restTemplateService.send(Constants.TARGET_CP_MASTER_API,
                propertyService.getCpMasterApiListRoleBindingsDeleteUrl()
                        .replace("{namespace}", params.getNamespace())
                        .replace("{name}", rbname),
                HttpMethod.DELETE, null, ResultStatus.class, params);
        } catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.NOT_FOUND.getCode()) {
                LOGGER.info("*** EXCEPTION FOR DELETE CLUSTER ROLE BINDING : NOT FOUND...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }
        return resultStatus;
    }

    public ResultStatus deleteUsers(Params params) {
        ResultStatus resultStatus = null;
        //Map resultMap = null;
        try {
            resultStatus = restTemplateService.send(org.container.platform.api.common.Constants.TARGET_COMMON_API,
                    Constants.URI_COMMON_API_DELETE_USER
                            .replace("{cluster:.+}", params.getCluster())
                            .replace("{namespace:.+}", params.getNamespace())
                            .replace("{userAuthId:.+}", params.getUserAuthId())
                            .replace("{userType:.+}", params.getUserType()),
                    HttpMethod.DELETE, null, ResultStatus.class, params);
        }
        catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.NOT_FOUND.getCode()) {
                LOGGER.info("*** EXCEPTION FOR DELETE USERS ROLE : NOT FOUND...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }

        return resultStatus;
    }

    public void updateSidecarResource(Params params, Users user) {
        Map map = new HashMap();

        map.put("userName", params.getRs_sa());
        map.put("roleName", params.getRs_role());
        map.put("nsName", params.getNamespace());
        map.put("roleGuid", UUID.randomUUID().toString());

        if (!propertyService.getSidecarRolesAdmin().equals(params.getRs_role())) {
            try {// before CFADMIN role 보유한다면..
                deleteRoleBinding(params, Constants.CFADMIN_ROLEBINDING_NAME.replace("{username}", params.getRs_sa()));
            } catch (Exception e) {
                if (Integer.valueOf(e.getMessage()) == CommonStatusCode.NOT_FOUND.getCode()) {
                    LOGGER.info("*** EXCEPTION FOR DELETE CLUSTER ROLE BINDING : NOT FOUND...");
                } else {
                    throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
                }
            }
        }

        // 사용자 DB rootNS ALL sidecarRoles 삭제
        deleteUsers(params);

        if (!Constants.NOT_ASSIGNED_ROLE.equals(params.getRs_role())){
            if (!propertyService.getSidecarRolesAdmin().equals(params.getRs_role())) {
                map.put("rbName", Constants.CFUSER_ROLEBINDING_NAME.replace("{username}", params.getRs_sa()));
                params.setYaml(templateService.convert("sidecar/create_userRoleBinding.ftl", map));
            } else {
                map.put("rbName", Constants.CFADMIN_ROLEBINDING_NAME.replace("{username}", params.getRs_sa()));
                params.setYaml(templateService.convert("sidecar/create_adminRoleBinding.ftl", map));
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
        }

        // db 사용자&롤 정보 조회
        boolean hasSidecarRole = false;
        List<Users> usersList = getUsersById(params).getItems();
        for(Users x : usersList) {
            if (propertyService.getSidecarRootNamespace().equals(x.getCpNamespace())
                    && user.getRoleSetCode().equals(x.getRoleSetCode())
                    && user.getClusterId().equals(x.getClusterId())) {
                hasSidecarRole = true;
            }
        }

        // db 사용자 정보 등록
        if(!hasSidecarRole){
            Users newUser = new Users(user.getClusterId(), user.getCpNamespace(), user.getUserId(), user.getUserAuthId(),
                    user.getUserType(), user.getRoleSetCode(), user.getServiceAccountName(), user.getSaSecret());

            resourceYamlService.createUsers(newUser);
        }
    }

    public void deleteSidecarResource(Params params, Users user) {
        Map map = new HashMap();

        map.put("userName", params.getRs_sa());
        map.put("roleName", params.getRs_role());
        map.put("nsName", params.getNamespace());
        map.put("roleGuid", UUID.randomUUID().toString());

        if (!Constants.NOT_ASSIGNED_ROLE.equals(params.getRs_role())) {
            throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
        }

        try {// CFADMIN role 보유한다면..
            deleteRoleBinding(params, Constants.CFADMIN_ROLEBINDING_NAME.replace("{username}", params.getRs_sa()));
        } catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.NOT_FOUND.getCode()) {
                LOGGER.info("*** EXCEPTION FOR DELETE CLUSTER ROLE BINDING : NOT FOUND...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }

        try {// CFUSER role 보유한다면..
            deleteRoleBinding(params, Constants.CFUSER_ROLEBINDING_NAME.replace("{username}", params.getRs_sa()));
        } catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.NOT_FOUND.getCode()) {
                LOGGER.info("*** EXCEPTION FOR DELETE CLUSTER ROLE BINDING : NOT FOUND...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }

        try {
            // service-account  생성 in root_namespace
            resourceYamlService.deleteServiceAccount(params);
        } catch (Exception e) {
            if (Integer.valueOf(e.getMessage()) == CommonStatusCode.CONFLICT.getCode()) {
                LOGGER.info("*** CREATE_SIDECAR_RESOURCE: SERVICE ACCOUNT ALREADY EXISTS WITH THAT NAME...");
            } else {
                throw new ResultStatusException(CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
        }

        // vault-user-token 등록
        //params.setUserType(AUTH_SUPER_ADMIN); // if AUTH_SUPER_ADMIN, vault path에서 cluster
        params.setUserType(AUTH_USER); // if AUTH_USER, vault path에서 cluster
        vaultService.deleteUserAccessToken(params);

        // 사용자 DB rootNS ALL sidecarRoles 삭제
        params.setUserType(user.getUserType());
        deleteUsers(params);
    }
}
