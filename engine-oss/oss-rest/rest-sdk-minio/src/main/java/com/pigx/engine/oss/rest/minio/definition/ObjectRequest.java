package com.pigx.engine.oss.rest.minio.definition;

import io.minio.ObjectArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public abstract class ObjectRequest<B extends ObjectArgs.Builder<B, A>, A extends ObjectArgs> extends BucketRequest<B, A> {

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
    public void prepare(B builder) {
        builder.object(getObjectName());
        super.prepare(builder);
    }
}
