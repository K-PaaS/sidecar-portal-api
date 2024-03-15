package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v2.applications.*;
import org.kpaas.sidecar.portal.api.common.Common;

public class ApplicationsServiceV2 extends Common {
    public AssociateApplicationRouteResponse associateRoute(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().associateRoute(AssociateApplicationRouteRequest.builder().build()).block();
    }

    public CopyApplicationResponse copy(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().copy(CopyApplicationRequest.builder().build()).block();
    }

    public CreateApplicationResponse create(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().create(CreateApplicationRequest.builder().build()).block();
    }

    public Void delete(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().delete(DeleteApplicationRequest.builder().build()).block();
    }

    /*public AssociateApplicationRouteResponse download(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV2().download(DownloadApplicationRequest.builder().build()).
    }*/
    /*public Void downloadDroplet(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token)).applicationsV2().downloadDroplet(DownloadApplicationDropletRequest.builder().build()).
    }*/
    public ApplicationEnvironmentResponse environment(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().environment(ApplicationEnvironmentRequest.builder().build()).block();
    }
    public GetApplicationResponse get(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().get(GetApplicationRequest.builder().build()).block();
    }
    public GetApplicationPermissionsResponse getPermissions(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().getPermissions(GetApplicationPermissionsRequest.builder().build()).block();
    }
    public ApplicationInstancesResponse instances(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().instances(ApplicationInstancesRequest.builder().build()).block();
    }
    public ListApplicationsResponse list(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().list(ListApplicationsRequest.builder().build()).block();
    }
    public ListApplicationRoutesResponse listRoutes(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().listRoutes(ListApplicationRoutesRequest.builder().build()).block();
    }
    public ListApplicationServiceBindingsResponse listServiceBindings(String listServiceBindings) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().listServiceBindings(ListApplicationServiceBindingsRequest.builder().build()).block();
    }
    public Void removeRoute(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().removeRoute(RemoveApplicationRouteRequest.builder().build()).block();
    }
    public Void removeServiceBinding(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().removeServiceBinding(RemoveApplicationServiceBindingRequest.builder().build()).block();
    }
    public RestageApplicationResponse restage(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().restage(RestageApplicationRequest.builder().build()).block();
    }
    public ApplicationStatisticsResponse statistics(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().statistics(ApplicationStatisticsRequest.builder().build()).block();
    }
    public SummaryApplicationResponse summary(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().summary(SummaryApplicationRequest.builder().build()).block();
    }
    public Void terminateInstance(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().terminateInstance(TerminateApplicationInstanceRequest.builder().build()).block();
    }
    public UpdateApplicationResponse update(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().update(UpdateApplicationRequest.builder().build()).block();
    }
    public UploadApplicationResponse upload(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().upload(UploadApplicationRequest.builder().build()).block();
    }
    public UploadApplicationDropletResponse uploadDroplet(String guid) {
        return cloudFoundryClient(tokenProvider()).applicationsV2().uploadDroplet(UploadApplicationDropletRequest.builder().build()).block();
    }
}
