package org.kpaas.sidecar.portal.api.login.model;

import lombok.Data;

@Data
public class Whoami {
    private String name;
    private String kind;
}