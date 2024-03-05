package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.client.v3.roles.RoleRelationships;
import org.cloudfoundry.client.v3.roles.RoleType;

public abstract class Role extends org.cloudfoundry.client.v3.roles.Role {
    @JsonProperty("relationships")
    public abstract RoleRelationships getRelationships();

    @JsonProperty("type")
    public abstract RoleType getType();
}
