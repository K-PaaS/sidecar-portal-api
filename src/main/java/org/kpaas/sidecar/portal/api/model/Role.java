package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.roles.RoleRelationships;
import org.cloudfoundry.client.v3.roles.RoleType;

import java.util.Map;

public class Role extends org.cloudfoundry.client.v3.roles.Role {

    @JsonProperty("relationships")
    public RoleRelationships relationships;

    @JsonProperty("type")
    public RoleType type;

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
    public RoleRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(RoleRelationships relationships) {
        this.relationships = relationships;
    }

    @Override
    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
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
        return "Role{" +
                "relationships=" + relationships +
                ", type=" + type +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", links=" + links +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
