package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.LastOperation;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.MaintenanceInfo;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.serviceinstances.ServiceInstanceRelationships;
import org.cloudfoundry.client.v3.serviceinstances.ServiceInstanceType;

import java.util.List;
import java.util.Map;

public class ServiceInstance extends org.cloudfoundry.client.v3.serviceinstances.ServiceInstance {
    @JsonProperty("dashboard_url")
    @Nullable
    public String dashboardUrl;

    @JsonProperty("last_operation")
    @Nullable
    public LastOperation lastOperation;

    @JsonProperty("maintenance_info")
    @Nullable
    public MaintenanceInfo maintenanceInfo;

    @JsonProperty("metadata")
    @Nullable
    public Metadata metadata;

    @JsonProperty("name")
    public String name;

    @JsonProperty("relationships")
    @Nullable
    public ServiceInstanceRelationships relationships;

    @JsonProperty("route_service_url")
    @Nullable
    public String routeServiceUrl;

    @JsonProperty("syslog_drain_url")
    @Nullable
    public String syslogDrainUrl;

    @JsonProperty("tags")
    @Nullable
    public List<String> tags;

    @JsonProperty("credentials")
    @Nullable
    public Map<String, ? extends Object> credentials;

    @JsonProperty("type")
    @Nullable
    public ServiceInstanceType type;

    @JsonProperty("upgrade_available")
    @Nullable
    public Boolean updateAvailable;

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
    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public void setDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
    }

    @Override
    public LastOperation getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(LastOperation lastOperation) {
        this.lastOperation = lastOperation;
    }

    @Override
    public MaintenanceInfo getMaintenanceInfo() {
        return maintenanceInfo;
    }

    public void setMaintenanceInfo(MaintenanceInfo maintenanceInfo) {
        this.maintenanceInfo = maintenanceInfo;
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
    public ServiceInstanceRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(ServiceInstanceRelationships relationships) {
        this.relationships = relationships;
    }

    @Override
    public String getRouteServiceUrl() {
        return routeServiceUrl;
    }

    public void setRouteServiceUrl(String routeServiceUrl) {
        this.routeServiceUrl = routeServiceUrl;
    }

    @Override
    public String getSyslogDrainUrl() {
        return syslogDrainUrl;
    }

    public void setSyslogDrainUrl(String syslogDrainUrl) {
        this.syslogDrainUrl = syslogDrainUrl;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public ServiceInstanceType getType() {
        return type;
    }

    public void setType(ServiceInstanceType type) {
        this.type = type;
    }

    @Override
    public Boolean getUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(Boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
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

    public Map<String, ? extends Object> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, ? extends Object> credentials) {
        this.credentials = credentials;
    }
    @Override
    public String toString() {
        return "ServiceInstance{" +
                "dashboardUrl='" + dashboardUrl + '\'' +
                ", lastOperation=" + lastOperation +
                ", maintenanceInfo=" + maintenanceInfo +
                ", metadata=" + metadata +
                ", name='" + name + '\'' +
                ", relationships=" + relationships +
                ", routeServiceUrl='" + routeServiceUrl + '\'' +
                ", syslogDrainUrl='" + syslogDrainUrl + '\'' +
                ", tags=" + tags +
                ", type=" + type +
                ", updateAvailable=" + updateAvailable +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", links=" + links +
                ", updatedAt='" + updatedAt + '\'' +
                ", credentials='" + credentials + '\'' +
                '}';
    }
}
