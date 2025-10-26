package com.pigx.engine.message.core.domain;

import com.pigx.engine.core.identity.domain.AbstractRest;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/domain/RestMapping.class */
public class RestMapping extends AbstractRest {
    private String mappingId;
    private String mappingCode;
    private String className;
    private String methodName;
    private String description;

    public String getMappingId() {
        return this.mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getMappingCode() {
        return this.mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestMapping that = (RestMapping) o;
        return Objects.equal(this.mappingId, that.mappingId);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.mappingId});
    }

    @Override // com.pigx.engine.core.identity.domain.AbstractRest
    public String toString() {
        return MoreObjects.toStringHelper(this).add("mappingId", this.mappingId).add("mappingCode", this.mappingCode).add("className", this.className).add("methodName", this.methodName).add("description", this.description).addValue(super.toString()).toString();
    }
}
