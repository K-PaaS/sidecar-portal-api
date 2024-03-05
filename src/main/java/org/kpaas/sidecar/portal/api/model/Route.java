package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.routes.Destination;
import org.cloudfoundry.client.v3.routes.Protocol;
import org.cloudfoundry.client.v3.routes.RouteRelationships;

import java.util.List;

public abstract class Route extends org.cloudfoundry.client.v3.routes.Route {

    @JsonProperty("destinations")
    @Nullable
    public abstract List<Destination> getDestinations();

    @JsonProperty("host")
    public abstract String getHost();

    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

    @JsonProperty("path")
    @Nullable
    public abstract String getPath();

    @JsonProperty("port")
    @Nullable
    public abstract Integer getPort();

    @JsonProperty("protocol")
    @Nullable
    public abstract Protocol getProtocol();

    @JsonProperty("relationships")
    public abstract RouteRelationships getRelationships();

    @JsonProperty("url")
    @Nullable
    public abstract String getUrl();
}
