package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.processes.HealthCheck;
import org.cloudfoundry.client.v3.processes.ProcessRelationships;

import java.util.Map;

public class Process extends org.cloudfoundry.client.v3.processes.Process {

    @JsonProperty("command")
    @Nullable
    public String command;

    @JsonProperty("disk_in_mb")
    @Nullable
    public Integer diskInMb;

    @JsonProperty("health_check")
    @Nullable
    public HealthCheck healthCheck;

    @JsonProperty("instances")
    @Nullable
    public Integer instances;

    @JsonProperty("memory_in_mb")
    @Nullable
    public Integer memoryInMb;

    @JsonProperty("metadata")
    @Nullable
    public Metadata metadata;

    @JsonProperty("relationships")
    @Nullable
    public ProcessRelationships relationships;

    @JsonProperty("type")
    @Nullable
    public String type;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonIgnore
    @JsonProperty("id")
    public String id;
    @JsonProperty("links")
    public Map<String, Link> links;
    @JsonProperty("updated_at")
    public String updatedAt;

    @Override
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public Integer getDiskInMb() {
        return diskInMb;
    }

    public void setDiskInMb(Integer diskInMb) {
        this.diskInMb = diskInMb;
    }

    @Override
    public HealthCheck getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(HealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }

    @Override
    public Integer getInstances() {
        return instances;
    }

    public void setInstances(Integer instances) {
        this.instances = instances;
    }

    @Override
    public Integer getMemoryInMb() {
        return memoryInMb;
    }

    public void setMemoryInMb(Integer memoryInMb) {
        this.memoryInMb = memoryInMb;
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public ProcessRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(ProcessRelationships relationships) {
        this.relationships = relationships;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Process{" +
                "command='" + command + '\'' +
                ", diskInMb=" + diskInMb +
                ", healthCheck=" + healthCheck +
                ", instances=" + instances +
                ", memoryInMb=" + memoryInMb +
                ", metadata=" + metadata +
                ", relationships=" + relationships +
                ", type='" + type + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", links=" + links +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
