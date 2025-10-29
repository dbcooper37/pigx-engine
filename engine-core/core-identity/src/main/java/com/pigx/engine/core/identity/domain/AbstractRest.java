package com.pigx.engine.core.identity.domain;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.domain.BaseModel;


public abstract class AbstractRest implements BaseModel {

    private String requestMethod;

    private String serviceId;

    private String url;

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .add("url", url)
                .toString();
    }
}
