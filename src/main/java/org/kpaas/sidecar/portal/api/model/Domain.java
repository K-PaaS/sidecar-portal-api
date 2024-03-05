package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.AllowNulls;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.domains.DomainRelationships;
import org.cloudfoundry.client.v3.domains.RouterGroup;
import org.cloudfoundry.client.v3.routes.Protocol;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Domain extends org.cloudfoundry.client.v3.domains.Domain {
    @JsonProperty("metadata")
    @Nullable
    public abstract Metadata getMetadata();

    @JsonProperty("name")
    public abstract String getName();

    @JsonProperty("relationships")
    @Nullable
    public abstract DomainRelationships getRelationships();

    @AllowNulls
    @JsonProperty("router_group")
    @Nullable
    public abstract RouterGroup getRouterGroup();

    @JsonProperty("supported_protocols")
    @Nullable
    public abstract List<Protocol> getSupportedProtocols();

    @JsonProperty("internal")
    @Nullable
    public abstract boolean isInternal();
}
