package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.cloudfoundry.client.v3.Lifecycle;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.applications.ApplicationRelationships;
import org.cloudfoundry.client.v3.applications.ApplicationState;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public class Application extends org.cloudfoundry.client.v3.applications.Application {
    @JsonProperty("lifecycle")
    public Lifecycle lifecycle;
    @JsonProperty("metadata")
    public Metadata metadata;
    @JsonProperty("name")
    public String name;
    @JsonProperty("relationships")
    public ApplicationRelationships relationships;
    @JsonProperty("state")
    public ApplicationState state;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonIgnore
    @JsonProperty("id")
    public String id;
    @JsonProperty("links")
    public Map<String, Link> links;
    @JsonProperty("updated_at")
    public String updatedAt;

    @JsonProperty("instances")
    public Integer instances;
    @JsonProperty("disk_in_mb")
    public Integer diskInMb;
    @JsonProperty("memory_in_mb")
    public Integer memoryInMb;
    @JsonProperty("environment_variables")
    public Map.Entry<String, ? extends String> environmentVariables;

    @Override
    public Lifecycle getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ApplicationRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(ApplicationRelationships relationships) {
        this.relationships = relationships;
    }

    @Override
    public ApplicationState getState() {
        return state;
    }

    public void setState(ApplicationState state) {
        this.state = state;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    @Override
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Integer getInstances() {
        return instances;
    }

    public void setInstances(Integer instances) {
        this.instances = instances;
    }

    public Integer getDiskInMb() {
        return diskInMb;
    }

    public void setDiskQuota(Integer diskInMb) {
        this.diskInMb = diskInMb;
    }

    public Integer getMemoryInMb() {
        return memoryInMb;
    }

    public void setMemoryInMb(Integer memoryInMb) {
        this.memoryInMb = memoryInMb;
    }

    public Map.Entry<String, ? extends String> getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(Map.Entry<String, ? extends String> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    @Override
    public String toString() {
        return "Application{" +
                "lifecycle=" + lifecycle +
                ", metadata=" + metadata +
                ", name='" + name + '\'' +
                ", relationships=" + relationships +
                ", state=" + state +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", links=" + links +
                ", updatedAt='" + updatedAt + '\'' +
                ", instances=" + instances +
                ", diskQuota=" + diskInMb +
                ", memory=" + memoryInMb +
                ", environmentVariables=" + environmentVariables +
                '}';
    }
}
