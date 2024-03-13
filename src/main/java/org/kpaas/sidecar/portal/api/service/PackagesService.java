package org.kpaas.sidecar.portal.api.service;

import org.cloudfoundry.client.v3.Relationship;
import org.cloudfoundry.client.v3.ToOneRelationship;
import org.cloudfoundry.client.v3.packages.*;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;

@Service
public class PackagesService extends Common {
    public CopyPackageResponse copy(String packageGuid, String appGuid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .packages()
                .copy(CopyPackageRequest
                        .builder()
                        .sourcePackageId(packageGuid)
                        .relationships(PackageRelationships.builder().application(ToOneRelationship.builder().data(Relationship.builder().id(appGuid).build()).build()).build())
                        .build())
                .block();
    }

    public CreatePackageResponse create(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .packages()
                .create(CreatePackageRequest
                        .builder()
                        .type(PackageType.BITS)
                        .relationships(PackageRelationships.builder()
                                .application(ToOneRelationship
                                        .builder()
                                        .data(Relationship
                                                .builder()
                                                .id(guid)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .block();
    }

    public String delete(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .packages()
                .delete(DeletePackageRequest
                        .builder()
                        .packageId(guid)
                        .build())
                .block();
    }

    public Flux<byte[]> download(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .packages()
                .download(DownloadPackageRequest
                        .builder()
                        .packageId(guid)
                        .build())
                ;
    }

    public GetPackageResponse get(String guid, String token) {
        return cloudFoundryClient(tokenProvider(token))
                .packages()
                .get(GetPackageRequest
                        .builder()
                        .packageId(guid)
                        .build())
                .block();
    }

    public ListPackagesResponse list(List<String> appGuids, List<String> orgGuids, List<String> spaceGuids, String token) {
        appGuids = stringListNullCheck(appGuids);
        orgGuids = stringListNullCheck(orgGuids);
        spaceGuids = stringListNullCheck(spaceGuids);

        return cloudFoundryClient(tokenProvider(token))
                .packages()
                .list(ListPackagesRequest
                        .builder()
                        .applicationIds(appGuids)
                        .organizationIds(orgGuids)
                        .spaceIds(spaceGuids)
                        .build())
                .block();
    }

    public ListPackageDropletsResponse listDroplets(String packageGuid, List<String> dropletGuids, String token) {
        dropletGuids = stringListNullCheck(dropletGuids);

        return cloudFoundryClient(tokenProvider(token))
                .packages()
                .listDroplets(ListPackageDropletsRequest
                        .builder()
                        .packageId(packageGuid)
                        .dropletIds(dropletGuids)
                        .build())
                .block();
    }

    public UploadPackageResponse upload(String guid, File file, String token) {
        return cloudFoundryClient(tokenProvider(token)).packages().upload(UploadPackageRequest.builder().packageId(guid).bits(file.toPath()).build()).block();
    }
}
