package com.pigx.engine.core.identity.domain;

import com.pigx.engine.core.definition.domain.BaseModel;
import com.google.common.base.MoreObjects;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/AbstractRest.class */
public abstract class AbstractRest implements BaseModel {
    private String requestMethod;
    private String serviceId;
    private String url;

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("requestMethod", this.requestMethod).add("serviceId", this.serviceId).add("url", this.url).toString();
    }
}
