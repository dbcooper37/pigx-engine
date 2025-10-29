package com.pigx.engine.oss.rest.minio.definition;

import io.minio.ObjectVersionArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;


public abstract class ObjectVersionRequest<B extends ObjectVersionArgs.Builder<B, A>, A extends ObjectVersionArgs> extends ObjectRequest<B, A> {

    @Schema(name = "版本ID")
    private String versionId;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public void prepare(B builder) {
        if (StringUtils.isNotBlank(getVersionId())) {
            builder.versionId(getVersionId());
        }
        super.prepare(builder);
    }
}
