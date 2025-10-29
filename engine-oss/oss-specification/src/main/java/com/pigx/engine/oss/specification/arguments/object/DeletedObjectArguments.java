package com.pigx.engine.oss.specification.arguments.object;

import com.google.common.base.MoreObjects;
import com.pigx.engine.oss.specification.core.arguments.OssArguments;
import io.swagger.v3.oas.annotations.media.Schema;


public class DeletedObjectArguments implements OssArguments {

    @Schema(name = "对象名称")
    private String objectName;

    @Schema(name = "对象版本ID")
    private String versionId;

    public DeletedObjectArguments() {
    }

    public DeletedObjectArguments(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", objectName)
                .add("versionId", versionId)
                .toString();
    }
}
