package org.kpaas.sidecar.portal.api.login;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.cloudfoundry.client.v3.roles.ListRolesResponse;
import org.cloudfoundry.client.v3.roles.RoleResource;
import org.container.platform.api.common.CommonService;
import org.container.platform.api.common.Constants;
import org.container.platform.api.common.MessageConstant;
import org.container.platform.api.common.model.*;
import org.container.platform.api.users.Users;

import org.container.platform.api.users.UsersList;
import org.kpaas.sidecar.portal.api.common.SidecarPropertyService;
import org.kpaas.sidecar.portal.api.common.SidecarResourceYamlService;
import org.kpaas.sidecar.portal.api.common.SidecarRestTemplateService;
import org.kpaas.sidecar.portal.api.common.model.Params;

import org.kpaas.sidecar.portal.api.service.OrganizationsServiceV3;
import org.kpaas.sidecar.portal.api.service.RolesServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.container.platform.api.common.Constants.AUTH_USER;
import static org.kpaas.sidecar.portal.api.common.Constants.URI_SIDECAR_API_PREFIX;


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
    @Qualifier("sidecarPropertyService")
    private SidecarPropertyService propertyService;

    @Autowired
    private SidecarResourceYamlService resourceYamlService;

    @Autowired
    private CommonService commonService;

    @Autowired
    @Qualifier("sidecarRestTemplateService")
    private SidecarRestTemplateService restTemplateService;

    @Autowired
    private OrganizationsServiceV3 organizationsServiceV3;

    @Autowired
    @Qualifier("authUtil")
    private AuthUtil authUtil;

    @Autowired
    private RolesServiceV3 rolesServiceV3;

    @PostMapping(value = {URI_SIDECAR_API_PREFIX + "/rolebindings"})
    @ResponseBody
    public Object CreateRoleBindings(@RequestBody Users user) {
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

        return (ResultStatus) commonService.setResultModel(new ResultStatus(), Constants.RESULT_STATUS_SUCCESS);
    }

    @PutMapping(value = {URI_SIDECAR_API_PREFIX + "/rolebindings"})
    @ResponseBody
    public Object UpdateRoleBindings(@RequestBody Users user) {
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

        resourceYamlService.updateSidecarResource(params, user);

        return (ResultStatus) commonService.setResultModel(new ResultStatus(), Constants.RESULT_STATUS_SUCCESS);

    }
    @DeleteMapping(value = {URI_SIDECAR_API_PREFIX + "/rolebindings"})
    @ResponseBody
    public Object DeleteRoleBindings(@RequestBody Users user) {
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

        resourceYamlService.deleteSidecarResource(params, user);

        return (ResultStatus) commonService.setResultModel(new ResultStatus(), Constants.RESULT_STATUS_SUCCESS);

    }

    //@ApiOperation(value = "Org 목록조회(Get Org List)", nickname = "getOrganizationsList")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(name = "params", value = "request parameters", required = true, dataType = "object", paramType = "body")
    //})
    @GetMapping(value = {URI_SIDECAR_API_PREFIX + "/orgs"})
    @ResponseBody
    public Object getOrganizationsList() {

        return organizationsServiceV3.list(null);

    }

    @GetMapping(value = {URI_SIDECAR_API_PREFIX + "/TBD"})
    @ResponseBody
    public Object getSicecarUsersListByRoleSetCode(@RequestBody Users user) {
        //request body check
        Assert.hasText(user.clusterId);
        Assert.hasText(user.cpNamespace);
        Assert.hasText(user.roleSetCode);

        //params attributes setting
        Params params = new Params();
        params.setCluster(user.clusterId);
        params.setNamespace(user.cpNamespace);
        params.setRs_role(user.roleSetCode);

        UsersList result ;
        try {

            String reqUrlParam = "?roleSetCode=" + params.getRs_role() .trim();

            try{
                result  =  restTemplateService.sendGlobal(org.kpaas.sidecar.portal.api.common.Constants.TARGET_COMMON_API,
                        "/clusters/{cluster:.+}/namespaces/{namespace:.+}/sidecarusers"
                                .replace("{cluster:.+}", params.getCluster())
                                .replace("{namespace:.+}", params.getNamespace()) + reqUrlParam,
                        HttpMethod.GET, null, UsersList.class, params);
            } catch (Exception e){
                throw e;
            }
        } catch (Exception e) {
            return new ResultStatus(Constants.RESULT_STATUS_FAIL, e.getMessage(),
                    CommonStatusCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
        }

        return result;
    }

    @GetMapping(value = {URI_SIDECAR_API_PREFIX + "/orgs/{orgGuid}/userlist"})
    @ResponseBody
    public Object getUserListByOrg(@PathVariable @ApiParam(value = "Org GUID", required = true)String orgGuid,
                                      @RequestParam(required = true, defaultValue = "") String clusterId,
                                      @RequestParam(required = true, defaultValue = "") String namespace) {
        long sstarttime = (new Date()).getTime();
        // roles in Org
        List<String> orgGuids = new ArrayList<String>();
        orgGuids.add(orgGuid);
        long starttime = (new Date()).getTime();
        ListRolesResponse roles = rolesServiceV3.list(orgGuids, null, null, null, null);
        long endtime = (new Date()).getTime();
        System.out.println("< sunny rolesService> starttime "+starttime + " endtime " + endtime + " diff " + (endtime -starttime));
        //users in CommonDB
        Params params = new Params();
        params.setCluster(clusterId);
        params.setNamespace(namespace);
        //UsersList dbUsers ;

        //try{
            starttime = (new Date()).getTime();
        UsersList dbUsers =  restTemplateService.sendGlobal(org.kpaas.sidecar.portal.api.common.Constants.TARGET_COMMON_API,
                    "/clusters/{cluster:.+}/namespaces/{namespace:.+}/usersList"
                            .replace("{cluster:.+}", params.getCluster())
                            .replace("{namespace:.+}", propertyService.getSidecarRootNamespace()) ,
                    HttpMethod.GET, null, UsersList.class, params);
            endtime = (new Date()).getTime();
            System.out.println("< sunny commonDB > starttime "+starttime + " endtime " + endtime + " diff " + (endtime -starttime));
            //dbUsers = (UsersList) users;
        //} catch (Exception e){
        //    throw e;
        //}
        Map<String, Users> userList  =  new HashMap();
        //Set<String> userIds = new HashSet<>();
        Users user ;
        String userKey;
        String userKeyLast;
        for( RoleResource role : roles.getResources()){
            userKey = role.getRelationships().getUser().getData().getId();
            if (!userList.containsKey(userKey)) {
                user = new Users();
                userList.put(userKey, user);

                user.setClusterId(clusterId);
                user.setCpNamespace(namespace);

                if ( userKey.contains("serviceaccount") ){
                    userKeyLast = userKey.split(":")[3];
                    user.setUserId(userKeyLast);
                    user.setServiceAccountName(userKeyLast);
                    user.setUserType("SA");
                }else {
                    user.setUserId(userKey);
                    user.setServiceAccountName(userKey);
                    user.setUserType("UA");
                }
                user.setRoleSetCode(role.getType().toString());

                for ( Users dbUser:  dbUsers.getItems()){
                    if (dbUser.getServiceAccountName().equals(user.getServiceAccountName())){
                        user.setUserId(dbUser.getUserId());
                        break;
                    }
                }
            } else {
                String roleSetCode = userList.get(userKey).getRoleSetCode();
                roleSetCode += ","+role.getType().toString();
                userList.get(userKey).setRoleSetCode(roleSetCode);
            }
        }
        long eendtime = (new Date()).getTime();
        System.out.println("< sunny rolesService> sstarttime "+sstarttime + " eendtime " + eendtime + " diff " + (eendtime - sstarttime));
        return userList;
    }

    @GetMapping(value = {URI_SIDECAR_API_PREFIX + "/orgs/{orgGuid}/userlistk8s"})
    @ResponseBody
    public Object getUserListByOrgK8S(@PathVariable @ApiParam(value = "Org GUID", required = true)String orgGuid,
                                   @RequestParam(required = true, defaultValue = "") String clusterId,
                                   @RequestParam(required = true, defaultValue = "") String namespace) {
        long sstarttime = (new Date()).getTime();
        // roles in Org
        List<String> orgGuids = new ArrayList<String>();
        orgGuids.add(orgGuid);

        //users in CommonDB, K8S
        Params params = new Params();
        params.setCluster(clusterId);
        params.setNamespace(namespace);

        long starttime = (new Date()).getTime();
        RolebindingsList roles =  (RolebindingsList) restTemplateService.send(org.kpaas.sidecar.portal.api.common.Constants.TARGET_CP_MASTER_API,
                propertyService.getCpMasterApiListRoleBindingsListUrl().replace("{namespace}", orgGuid),
                HttpMethod.GET, null, RolebindingsList.class, params);
        //RolebindingsList roles = commonService.setResultObject(responseMap, RolebindingsList.class);
        long endtime = (new Date()).getTime();
        System.out.println("< sunny rolesService> starttime "+starttime + " endtime " + endtime + " diff " + (endtime -starttime));

        //UsersList dbUsers ;
        //try{
        starttime = (new Date()).getTime();
        UsersList dbUsers =  restTemplateService.sendGlobal(org.kpaas.sidecar.portal.api.common.Constants.TARGET_COMMON_API,
                "/clusters/{cluster:.+}/namespaces/{namespace:.+}/usersList"
                        .replace("{cluster:.+}", params.getCluster())
                        .replace("{namespace:.+}", propertyService.getSidecarRootNamespace()) ,
                HttpMethod.GET, null, UsersList.class, params);
        endtime = (new Date()).getTime();
        System.out.println("< sunny commonDB > starttime "+starttime + " endtime " + endtime + " diff " + (endtime -starttime));
        //dbUsers = (UsersList) users;
        //} catch (Exception e){
        //    throw e;
        //}

        Map<String, Users> userList  =  new HashMap();
        //Set<String> userIds = new HashSet<>();
        Users user ;
        String userKey;
        String roleName;
        String newRoleName;

        for( RolebindingsItem  role : roles.getItems()){
            userKey = role.getSubjects().get(0).getName();
            roleName =  role.getRoleRef().getName();

            if (!roleName.equals(propertyService.getSidecarRolesAdmin()) && roleName.contains("organization")) { //korifi-controllers-admin skip
                newRoleName = roleName.replace("korifi-controllers-organization-user", "organization_user")
                        .replace("korifi-controllers-organization-manager", "organization_manager")
                        .replace("korifi-controllers-space-manager", "space_manager")
                        .replace("korifi-controllers-space-manager", "space_developer");
                if (!userList.containsKey(userKey)) {
                    user = new Users();
                    userList.put(userKey, user);

                    user.setClusterId(clusterId);
                    user.setCpNamespace(namespace);
                    user.setRoleSetCode(newRoleName);

                    if (role.getSubjects().get(0).getKind().equals("ServiceAccount")) {
                        user.setUserId(userKey);
                        user.setServiceAccountName(userKey);
                        user.setUserType("SA");
                    } else {
                        user.setUserId(userKey);
                        user.setServiceAccountName(userKey);
                        user.setUserType("UA");
                    }

                    for (Users dbUser : dbUsers.getItems()) {
                        if (dbUser.getServiceAccountName().equals(user.getServiceAccountName())) {
                            user.setUserId(dbUser.getUserId());
                            break;
                        }
                    }
                } else {
                    String roleSetCode = userList.get(userKey).getRoleSetCode();
                    roleSetCode += "," + newRoleName;
                    userList.get(userKey).setRoleSetCode(roleSetCode);
                }
            }
        }
        long eendtime = (new Date()).getTime();
        System.out.println("< sunny rolesService> sstarttime "+sstarttime + " eendtime " + eendtime + " diff " + (eendtime - sstarttime));
        return userList;
    }
}

@Data
class RolebindingsList{

    private String resultCode;
    private String resultMessage;
    private Integer httpStatusCode;
    private String detailMessage;
    private Map metadata;
    private CommonItemMetaData itemMetaData;
    private List<RolebindingsItem> items;
}

@Data
class RolebindingsItem{

    private String name;
    private String namespace;
    private String creationTimestamp;
    private String labels;
    private RoleRef roleRef;
    private List<Subjects> subjects;

    @JsonIgnore
    private CommonMetaData metadata;

    @JsonIgnore
    private CommonSpec spec;

    public String getNamespace() {
        return metadata.getNamespace();
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() { return metadata.getName(); }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationTimestamp() {
        return metadata.getCreationTimestamp();
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public CommonMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(CommonMetaData metadata) {
        this.metadata = metadata;
    }

    public CommonSpec getSpec() {
        return spec;
    }

    public void setSpec(CommonSpec spec) {
        this.spec = spec;
    }

}

@Data
class RoleRef {

    private String apiGroup;
    private String name;
    private String kind;

}
@Data
class Subjects {

    private String apiGroup;
    private String name;
    private String kind;

}
