package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Lifecycle;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.applications.ApplicationState;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Application extends org.cloudfoundry.client.v3.applications.Application {


    /**
     * The lifecycle
     */
    @JsonProperty("lifecycle")
    @Nullable
    public abstract Lifecycle getLifecycle();

    /**
     * The metadata
     */
    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

    /**
     * The name
     */
    @JsonProperty("name")
    public abstract String getName();

    /**
     * The relationships
     */
    @JsonProperty("relationships")
    @Nullable
    public abstract ApplicationRelationships getRelationships();

    /**
     * The state
     */
    @JsonProperty("state")
    @Nullable
    public abstract ApplicationState getState();

    /**
     * The Instances
     */
    @JsonProperty("instances")
    @Nullable
    public abstract Integer getInstances();

    /**
     * The Disk Quota
     */
    @JsonProperty("diskQuota")
    @Nullable
    public abstract Integer getDiskQuota();

    /**
     * The Memory
     */
    @JsonProperty("memory")
    @Nullable
    public abstract Integer getMemory();

    @JsonProperty("environmentVariables")
    @Nullable
    public abstract Map.Entry<String, ? extends String> getEnvironmentVariables();


}
