package com.pigx.engine.oss.specification.arguments.base;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;


public abstract class ObjectVersionArguments extends ObjectArguments {

    @Schema(name = "版本ID")
    private String versionId;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("versionId", versionId)
                .toString();
    }
}
