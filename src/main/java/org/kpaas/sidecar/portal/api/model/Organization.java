package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.organizations.OrganizationRelationships;
import org.cloudfoundry.client.v3.organizations.OrganizationStatus;

public abstract class Organization extends org.cloudfoundry.client.v3.organizations.Organization {
    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

    @JsonProperty("name")
    public abstract String getName();

    @JsonProperty("relationships")
    @Nullable
    public abstract OrganizationRelationships getRelationships();

    @JsonProperty("status")
    @Nullable
    public abstract OrganizationStatus getStatus();

    @JsonProperty("suspended")
    @Nullable
    public abstract Boolean getSuspended();
}
