package org.kpaas.sidecar.portal.api.common;

public class Constants extends org.container.platform.api.common.Constants{

    public static final String TARGET_SIDECAR_API = "sidecarApi";
    public static final String URI_SIDECAR_API_WHOAMI = "/whoami";
    public static final String URI_SIDECAR_API_PREFIX = "sidecar";
    //public static final String SIDECAR_ROOT_NAMESPACE = "kpaas";
    public static final String CFADMIN_ROLEBINDING_NAME= "admin-{username}";
    public static final String CFUSER_ROLEBINDING_NAME= "user-{username}";
    public static final String SA_TOKEN_NAME= "sidecar-{username}-token";
    public static final String[] SIDECAR_PERMIT_PATH_LIST = new String[]{ URI_SIDECAR_API_WHOAMI, "/sidecar", "/logout"};

    public enum SidecarStatus {
        ACTIVE("A"),
        DISABLED("D");

        private final String initial;
        SidecarStatus(String initial) {
            this.initial = initial;
        }
        public String getInitial() {
            return initial;
        }

    }

}
