package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.LastOperation;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingRelationships;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingType;

public abstract class ServiceBinding extends org.cloudfoundry.client.v3.servicebindings.ServiceBinding {
    @JsonProperty("name")
    @Nullable
    public abstract String getName();

    @JsonProperty("type")
    @Nullable
    public abstract ServiceBindingType getType();

    @JsonProperty("last_operation")
    @Nullable
    public abstract LastOperation getLastOperation();

    @JsonProperty("relationships")
    @Nullable
    public abstract ServiceBindingRelationships getRelationships();

    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

}
