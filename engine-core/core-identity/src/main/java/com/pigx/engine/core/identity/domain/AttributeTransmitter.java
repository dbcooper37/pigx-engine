package com.pigx.engine.core.identity.domain;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/AttributeTransmitter.class */
public class AttributeTransmitter implements Serializable {
    private String attributeId;
    private String attributeCode;
    private String attributeName;
    private String webExpression;
    private String permissions;
    private String url;
    private String requestMethod;
    private String serviceId;

    public String getAttributeId() {
        return this.attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeCode() {
        return this.attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getWebExpression() {
        return this.webExpression;
    }

    public void setWebExpression(String webExpression) {
        this.webExpression = webExpression;
    }

    public String getPermissions() {
        return this.permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttributeTransmitter that = (AttributeTransmitter) o;
        return Objects.equal(this.attributeId, that.attributeId);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.attributeId});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("attributeId", this.attributeId).add("attributeCode", this.attributeCode).add("attributeName", this.attributeName).add(SystemConstants.AUTHORITIES, this.webExpression).add("permissions", this.permissions).add("url", this.url).add("requestMethod", this.requestMethod).add("serviceId", this.serviceId).toString();
    }
}
