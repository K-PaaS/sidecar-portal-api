package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.applications.*;
import org.cloudfoundry.reactor.TokenProvider;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Application;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ApplicationsServiceV3 extends Common{
    public CreateApplicationResponse create(Application app) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .create(CreateApplicationRequest
                        .builder()
                        .name(app.getName())
                        .relationships(app.getRelationships())
                        .build())
                .block();
    }


    public String delete(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .delete(DeleteApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationCurrentDropletResponse getCurrentDroplet(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .getCurrentDroplet(GetApplicationCurrentDropletRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationEnvironmentResponse getEnvironment(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .getEnvironment(GetApplicationEnvironmentRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationProcessResponse getProcess(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .getProcess(GetApplicationProcessRequest
                        .builder()
                        .applicationId(guid)
                        .type("web")
                        .build())
                .block();
    }

    public GetApplicationSshEnabledResponse getSshEnabled(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .getSshEnabled(GetApplicationSshEnabledRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationProcessStatisticsResponse getProcessStatistics(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .getProcessStatistics(GetApplicationProcessStatisticsRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public ListApplicationsResponse list(List<String> names, List<String> orgGuids, List<String> spaceGuids) throws InterruptedException {
        names = stringListNullCheck(names);
        orgGuids = stringListNullCheck(orgGuids);
        spaceGuids = stringListNullCheck(spaceGuids);

        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .list(ListApplicationsRequest
                        .builder()
                        .names(names)
                        .organizationIds(orgGuids)
                        .spaceIds(spaceGuids)
                        .build())
                .block();
    }

    public ListApplicationProcessesResponse listProcesses(String appGuid, List<String> processGuids) {
        processGuids = stringListNullCheck(processGuids);

        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .listProcesses(ListApplicationProcessesRequest
                        .builder()
                        .applicationId(appGuid)
                        .processId(processGuids)
                        .types("web")
                        .build())
                .block();
    }

    public ListApplicationTasksResponse listTasks(String appGuid, List<String> names, List<String> sequenceGuids, List<String> taskGuids) {
        names = stringListNullCheck(names);
        sequenceGuids = stringListNullCheck(sequenceGuids);
        taskGuids = stringListNullCheck(taskGuids);

        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .listTasks(ListApplicationTasksRequest
                        .builder()
                        .applicationId(appGuid)
                        .names(names)
                        .sequenceIds(sequenceGuids)
                        .taskIds(taskGuids)
                        .build())
                .block();
    }

    public RestartApplicationResponse restart(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .restart(RestartApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public ScaleApplicationResponse scale(Application app, String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .scale(ScaleApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .type("web")
                        .instances(app.getInstances())
                        .diskInMb(app.getDiskInMb())
                        .memoryInMb(app.getMemoryInMb())
                        .build())
                .block();
    }

    public SetApplicationCurrentDropletResponse setCurrentDroplet(String appGuid, String dropletGuid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .setCurrentDroplet(SetApplicationCurrentDropletRequest
                        .builder()
                        .applicationId(appGuid)
                        .data(Relationship.builder().id(dropletGuid).build())
                        .build())
                .block();
    }
    public SetApplicationCurrentDropletResponse setCurrentDroplet(String appGuid, String dropletGuid, TokenProvider tokenProvider) {
        return cloudFoundryClient(tokenProvider)
                .applicationsV3()
                .setCurrentDroplet(SetApplicationCurrentDropletRequest
                        .builder()
                        .applicationId(appGuid)
                        .data(Relationship.builder().id(dropletGuid).build())
                        .build())
                .block();
    }

    public StartApplicationResponse start(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .start(StartApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }
    public StartApplicationResponse start(String guid, TokenProvider tokenProvider) {
        return cloudFoundryClient(tokenProvider)
                .applicationsV3()
                .start(StartApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public StopApplicationResponse stop(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .stop(StopApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public UpdateApplicationResponse update(Application app, String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .update(UpdateApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .name(app.getName())
                        .build())
                .block();
    }

    public UpdateApplicationEnvironmentVariablesResponse updateEnvironmentVariables(Application app, String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .updateEnvironmentVariables(UpdateApplicationEnvironmentVariablesRequest
                        .builder()
                        .applicationId(guid)
                        .var(app.getEnvironmentVariables())
                        .build())
                .block();
    }

    public ListApplicationRoutesResponse listRoutes(String appGuid, List<String> domainGuids, List<String> hosts, List<String> orgGuids, List<String> spaceGuids) {
        if ( domainGuids == null || domainGuids.isEmpty() ){
            domainGuids = new ArrayList<>();
        }
        if ( hosts == null || hosts.isEmpty() ){
            hosts = new ArrayList<>();
        }
        if ( orgGuids == null || orgGuids.isEmpty() ){
            orgGuids = new ArrayList<>();
        }
        if ( spaceGuids == null || spaceGuids.isEmpty() ){
            spaceGuids = new ArrayList<>();
        }

        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .listRoutes(ListApplicationRoutesRequest
                        .builder()
                        .applicationId(appGuid)
                        .domainIds(domainGuids)
                        .hosts(hosts)
                        .organizationIds(orgGuids)
                        .spaceIds(spaceGuids)
                        .build())
                .block();
    }

    public GetApplicationResponse get(String guid) {
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .get(GetApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationCurrentDropletRelationshipResponse getCurrentDropletRelationship(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV3().getCurrentDropletRelationship(GetApplicationCurrentDropletRelationshipRequest.builder().build()).block();
    }

    public GetApplicationFeatureResponse getFeature(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV3().getFeature(GetApplicationFeatureRequest.builder().build()).block();
    }
    public GetApplicationPermissionsResponse getPermissions(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV3().getPermissions(GetApplicationPermissionsRequest.builder().build()).block();
    }




    public ListApplicationBuildsResponse listBuilds() {
        return cloudFoundryClient(tokenProvider()).applicationsV3().listBuilds(ListApplicationBuildsRequest.builder().build()).block();
    }
    public ListApplicationDropletsResponse listDroplets() {
        return cloudFoundryClient(tokenProvider()).applicationsV3().listDroplets(ListApplicationDropletsRequest.builder().build()).block();
    }
    public ListApplicationFeaturesResponse listFeatures() {
        return cloudFoundryClient(tokenProvider()).applicationsV3().listFeatures(ListApplicationFeaturesRequest.builder().build()).block();
    }
    public ListApplicationPackagesResponse listPackages(String appGuid, List<String> packageGuids) {
        packageGuids = stringListNullCheck(packageGuids);
        return cloudFoundryClient(tokenProvider())
                .applicationsV3()
                .listPackages(ListApplicationPackagesRequest
                        .builder()
                        .applicationId(appGuid)
                        .packageIds(packageGuids)
                        .build())
                .block();
    }






    public Void terminateInstance(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV3().terminateInstance(TerminateApplicationInstanceRequest.builder().build()).block();
    }


    public UpdateApplicationFeatureResponse updateFeature(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV3().updateFeature(UpdateApplicationFeatureRequest.builder().build()).block();
    }


}
