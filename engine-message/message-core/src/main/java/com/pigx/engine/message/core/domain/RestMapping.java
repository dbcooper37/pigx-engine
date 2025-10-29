package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pigx.engine.core.identity.domain.AbstractRest;


public class RestMapping extends AbstractRest {

    private String mappingId;

    private String mappingCode;

    private String className;

    private String methodName;

    private String description;

    public RestMapping() {
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getMappingCode() {
        return mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestMapping that = (RestMapping) o;
        return Objects.equal(mappingId, that.mappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mappingId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mappingId", mappingId)
                .add("mappingCode", mappingCode)
                .add("className", className)
                .add("methodName", methodName)
                .add("description", description)
                .addValue(super.toString())
                .toString();
    }
}
