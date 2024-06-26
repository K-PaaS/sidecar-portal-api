package org.kpaas.sidecar.portal.api.model;

import org.cloudfoundry.Nullable;

import java.util.List;
import java.util.Map;

public class ApplicationService {
    public String serviceBindingGuid;
    public String serviceInstanceGuid;
    public String serviceInstanceName;

    public Map<String, Object> credentials;

    public String getServiceBindingGuid() {
        return serviceBindingGuid;
    }

    public void setServiceBindingGuid(String serviceBindingGuid) {
        this.serviceBindingGuid = serviceBindingGuid;
    }

    public String getServiceInstanceGuid() {
        return serviceInstanceGuid;
    }

    public void setServiceInstanceGuid(String serviceInstanceGuid) {
        this.serviceInstanceGuid = serviceInstanceGuid;
    }

    public String getServiceInstanceName() {
        return serviceInstanceName;
    }

    public void setServiceInstanceName(String serviceInstanceName) {
        this.serviceInstanceName = serviceInstanceName;
    }

    @Override
    public String toString() {
        return "ApplicationService{" +
                "serviceBindingGuid='" + serviceBindingGuid + '\'' +
                ", serviceInstanceGuid='" + serviceInstanceGuid + '\'' +
                ", serviceInstanceName='" + serviceInstanceName + '\'' +
                '}';
    }

    public ApplicationService(String serviceBindingGuid, String serviceInstanceGuid, String serviceInstanceName, Map<String, Object> credentials) {
        this.serviceBindingGuid = serviceBindingGuid;
        this.serviceInstanceGuid = serviceInstanceGuid;
        this.serviceInstanceName = serviceInstanceName;
        this.credentials = credentials;
    }
}
