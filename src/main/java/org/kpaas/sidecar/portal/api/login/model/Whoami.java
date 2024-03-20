package org.kpaas.sidecar.portal.api.login.model;

import lombok.Data;
import org.container.platform.api.common.Constants;
import org.kpaas.sidecar.portal.api.common.model.Params;

@Data
public class Whoami {
    private String name = Constants.EMPTY_STRING;
    private String kind = Constants.EMPTY_STRING;
    private String token = Constants.EMPTY_STRING;

    private Params params = new Params();

    public Whoami(){
        this.name = Constants.EMPTY_STRING;
        this.kind = Constants.EMPTY_STRING;
        this.token = Constants.EMPTY_STRING;

        this.params = new Params();

    }
}
