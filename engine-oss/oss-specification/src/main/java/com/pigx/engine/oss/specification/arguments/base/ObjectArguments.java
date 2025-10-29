package com.pigx.engine.oss.specification.arguments.base;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public abstract class ObjectArguments extends BucketArguments {

    @NotBlank(message = "对象名称不能为空")
    @Schema(name = "对象名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String objectName;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("objectName", objectName)
                .toString();
    }
}
