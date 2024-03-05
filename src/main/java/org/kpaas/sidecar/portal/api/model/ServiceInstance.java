package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.LastOperation;
import org.cloudfoundry.client.v3.MaintenanceInfo;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.serviceinstances.ServiceInstanceRelationships;
import org.cloudfoundry.client.v3.serviceinstances.ServiceInstanceType;

import java.util.List;

public abstract class ServiceInstance extends org.cloudfoundry.client.v3.serviceinstances.ServiceInstance {
    @JsonProperty("dashboard_url")
    @Nullable
    public abstract String getDashboardUrl();

    @JsonProperty("last_operation")
    @Nullable
    public abstract LastOperation getLastOperation();

    @JsonProperty("maintenance_info")
    @Nullable
    public abstract MaintenanceInfo getMaintenanceInfo();

    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

    @JsonProperty("name")
    public abstract String getName();

    @JsonProperty("relationships")
    @Nullable
    public abstract ServiceInstanceRelationships getRelationships();

    @JsonProperty("route_service_url")
    @Nullable
    public abstract String getRouteServiceUrl();

    @JsonProperty("syslog_drain_url")
    @Nullable
    public abstract String getSyslogDrainUrl();

    @JsonProperty("tags")
    @Nullable
    public abstract List<String> getTags();

    @JsonProperty("type")
    @Nullable
    public abstract ServiceInstanceType getType();

    @JsonProperty("upgrade_available")
    @Nullable
    public abstract Boolean getUpdateAvailable();

}
