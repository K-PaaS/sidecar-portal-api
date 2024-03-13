package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.LastOperation;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingRelationships;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingType;

import java.util.Map;

public class ServiceBinding extends org.cloudfoundry.client.v3.servicebindings.ServiceBinding {

    @JsonProperty("name")
    @Nullable
    public String name;

    @JsonProperty("type")
    @Nullable
    public ServiceBindingType type;

    @JsonProperty("last_operation")
    @Nullable
    public LastOperation lastOperation;

    @JsonProperty("relationships")
    @Nullable
    public ServiceBindingRelationships relationships;

    @JsonProperty("metadata")
    @Nullable
    public Metadata metadata;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ServiceBindingType getType() {
        return type;
    }

    public void setType(ServiceBindingType type) {
        this.type = type;
    }

    @Override
    public LastOperation getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(LastOperation lastOperation) {
        this.lastOperation = lastOperation;
    }

    @Override
    public ServiceBindingRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(ServiceBindingRelationships relationships) {
        this.relationships = relationships;
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
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
        return "ServiceBinding{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", lastOperation=" + lastOperation +
                ", relationships=" + relationships +
                ", metadata=" + metadata +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", links=" + links +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
