package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.applications.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.kpaas.sidecar.portal.api.model.Application;
import org.springframework.stereotype.Service;


@Service
public class ApplicationsServiceV3 extends Common{
    public CreateApplicationResponse create(Application app, String token) {

        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .create(CreateApplicationRequest
                        .builder()
                        .name(app.getName())
                        .relationships(app.getRelationships())
                        .build())
                .block();
    }


    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .delete(DeleteApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationCurrentDropletResponse getCurrentDroplet(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .getCurrentDroplet(GetApplicationCurrentDropletRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationEnvironmentResponse getEnvironment(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .getEnvironment(GetApplicationEnvironmentRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationProcessResponse getProcess(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .getProcess(GetApplicationProcessRequest
                        .builder()
                        .applicationId(guid)
                        .type("web")
                        .build())
                .block();
    }

    public GetApplicationSshEnabledResponse getSshEnabled(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .getSshEnabled(GetApplicationSshEnabledRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationProcessStatisticsResponse getProcessStatistics(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .getProcessStatistics(GetApplicationProcessStatisticsRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public ListApplicationsResponse list(String spaceGuid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .list(ListApplicationsRequest
                        .builder()
                        .spaceId(spaceGuid)
                        .build())
                .block();
    }

    public ListApplicationProcessesResponse listProcesses(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .listProcesses(ListApplicationProcessesRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public ListApplicationTasksResponse listTasks(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .listTasks(ListApplicationTasksRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public RestartApplicationResponse restart(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .restart(RestartApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public ScaleApplicationResponse scale(Application app, String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .scale(ScaleApplicationRequest
                        .builder()
                        .applicationId(guid)
                        //.type("web")
                        .instances(app.getInstances())
                        .diskInMb(app.getDiskQuota())
                        .memoryInMb(app.getMemory())
                        .build())
                .block();
    }

    public SetApplicationCurrentDropletResponse setCurrentDroplet(String appGuid, String dropletGuid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .setCurrentDroplet(SetApplicationCurrentDropletRequest
                        .builder()
                        .applicationId(appGuid)
                        .data(Relationship.builder().id(dropletGuid).build())
                        .build())
                .block();
    }

    public StartApplicationResponse start(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .start(StartApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public StopApplicationResponse stop(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .stop(StopApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public UpdateApplicationResponse update(Application app, String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .update(UpdateApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .name(app.getName())
                        .build())
                .block();
    }

    public UpdateApplicationEnvironmentVariablesResponse updateEnvironmentVariables(Application app, String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .updateEnvironmentVariables(UpdateApplicationEnvironmentVariablesRequest
                        .builder()
                        .applicationId(guid)
                        .var(app.getEnvironmentVariables())
                        .build())
                .block();
    }

    public ListApplicationRoutesResponse listRoutes(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .listRoutes(ListApplicationRoutesRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .get(GetApplicationRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }

    public GetApplicationCurrentDropletRelationshipResponse getCurrentDropletRelationship(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().getCurrentDropletRelationship(GetApplicationCurrentDropletRelationshipRequest.builder().build()).block();
    }

    public GetApplicationFeatureResponse getFeature(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().getFeature(GetApplicationFeatureRequest.builder().build()).block();
    }
    public GetApplicationPermissionsResponse getPermissions(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().getPermissions(GetApplicationPermissionsRequest.builder().build()).block();
    }




    public ListApplicationBuildsResponse listBuilds(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().listBuilds(ListApplicationBuildsRequest.builder().build()).block();
    }
    public ListApplicationDropletsResponse listDroplets(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().listDroplets(ListApplicationDropletsRequest.builder().build()).block();
    }
    public ListApplicationFeaturesResponse listFeatures(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().listFeatures(ListApplicationFeaturesRequest.builder().build()).block();
    }
    public ListApplicationPackagesResponse listPackages(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .applicationsV3()
                .listPackages(ListApplicationPackagesRequest
                        .builder()
                        .applicationId(guid)
                        .build())
                .block();
    }






    public Void terminateInstance(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().terminateInstance(TerminateApplicationInstanceRequest.builder().build()).block();
    }


    public UpdateApplicationFeatureResponse updateFeature(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV3().updateFeature(UpdateApplicationFeatureRequest.builder().build()).block();
    }


}
