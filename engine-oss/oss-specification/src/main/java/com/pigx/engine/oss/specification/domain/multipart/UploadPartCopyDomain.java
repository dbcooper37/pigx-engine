package com.pigx.engine.oss.specification.domain.multipart;

import com.google.common.base.MoreObjects;
import com.pigx.engine.oss.specification.domain.base.PartDomain;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "分片上传拷贝返回结果域对象", title = "分片上传拷贝返回结果域对象")
public class UploadPartCopyDomain extends PartDomain {

    @Schema(name = "分片上传ID")
    private String uploadId;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uploadId", uploadId)
                .toString();
    }
}
