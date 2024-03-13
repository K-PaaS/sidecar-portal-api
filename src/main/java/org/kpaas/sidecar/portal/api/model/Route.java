package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.routes.Destination;
import org.cloudfoundry.client.v3.routes.Protocol;
import org.cloudfoundry.client.v3.routes.RouteRelationships;

import java.util.List;
import java.util.Map;

public class Route extends org.cloudfoundry.client.v3.routes.Route {

    @JsonProperty("destinations")
    @Nullable
    public List<Destination> destinations;

    @JsonProperty("host")
    public String host;

    @JsonProperty("metadata")
    @Nullable
    public Metadata metadata;

    @JsonProperty("path")
    @Nullable
    public String path;

    @JsonProperty("port")
    @Nullable
    public Integer port;

    @JsonProperty("protocol")
    @Nullable
    public Protocol protocol;

    @JsonProperty("relationships")
    public RouteRelationships relationships;

    @JsonProperty("url")
    @Nullable
    public String url;

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
    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public RouteRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(RouteRelationships relationships) {
        this.relationships = relationships;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "Route{" +
                "destinations=" + destinations +
                ", host='" + host + '\'' +
                ", metadata=" + metadata +
                ", path='" + path + '\'' +
                ", port=" + port +
                ", protocol=" + protocol +
                ", relationships=" + relationships +
                ", url='" + url + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", links=" + links +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
