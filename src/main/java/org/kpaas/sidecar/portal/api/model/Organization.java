package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.organizations.OrganizationRelationships;
import org.cloudfoundry.client.v3.organizations.OrganizationStatus;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public class Organization extends org.cloudfoundry.client.v3.organizations.Organization {

    @JsonProperty("metadata")
    public Metadata metadata;
    @JsonProperty("name")
    public String name;

    @JsonProperty("relationships")
    @Nullable
    public OrganizationRelationships relationships;

    @JsonProperty("status")
    @Nullable
    public OrganizationStatus status;

    @JsonProperty("suspended")
    @Nullable
    public Boolean suspended;

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
    public OrganizationRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(OrganizationRelationships relationships) {
        this.relationships = relationships;
    }

    @Override
    public OrganizationStatus getStatus() {
        return status;
    }

    public void setStatus(OrganizationStatus status) {
        this.status = status;
    }

    @Override
    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
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
        return "Organization{" +
                "metadata=" + metadata +
                ", name='" + name + '\'' +
                ", relationships=" + relationships +
                ", status=" + status +
                ", suspended=" + suspended +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", links=" + links +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
