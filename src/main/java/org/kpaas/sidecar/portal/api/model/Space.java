package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.spaces.SpaceRelationships;

public abstract class Space extends org.cloudfoundry.client.v3.spaces.Space {

    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

    @JsonProperty("name")
    public abstract String getName();

    @JsonProperty("relationships")
    @Nullable
    public abstract SpaceRelationships getRelationships();
}
