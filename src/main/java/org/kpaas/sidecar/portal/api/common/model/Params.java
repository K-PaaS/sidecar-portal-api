package org.kpaas.sidecar.portal.api.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kpaas.sidecar.portal.api.common.Constants;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class Params extends org.container.platform.api.common.model.Params{
    public Params(){
        super();

    }

    public String orgName = Constants.EMPTY_STRING;
    public String spaceName = Constants.EMPTY_STRING;

    public String rs_orgName = Constants.EMPTY_STRING;
    public String rs_spaceName = Constants.EMPTY_STRING;
    public String rs_targetNamespace = Constants.EMPTY_STRING;

    public boolean hasSidecarAuth =  false;
    public List<GrantedAuthority> Roles;

    public Constants.SidecarStatus sidecarStatus = Constants.SidecarStatus.DISABLED;
}
