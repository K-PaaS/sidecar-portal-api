package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.processes.HealthCheck;
import org.cloudfoundry.client.v3.processes.ProcessRelationships;

public abstract class Process extends org.cloudfoundry.client.v3.processes.Process {

    @JsonProperty("command")
    @Nullable
    public abstract String getCommand();

    @JsonProperty("disk_in_mb")
    @Nullable
    public abstract Integer getDiskInMb();

    @JsonProperty("health_check")
    @Nullable
    public abstract HealthCheck getHealthCheck();

    @JsonProperty("instances")
    @Nullable
    public abstract Integer getInstances();

    @JsonProperty("memory_in_mb")
    @Nullable
    public abstract Integer getMemoryInMb();

    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

    @JsonProperty("relationships")
    @Nullable
    public abstract ProcessRelationships getRelationships();

    @JsonProperty("type")
    @Nullable
    public abstract String getType();
}
