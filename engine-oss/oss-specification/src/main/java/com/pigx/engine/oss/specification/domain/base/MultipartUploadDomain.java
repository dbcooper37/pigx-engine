package com.pigx.engine.oss.specification.domain.base;

import io.swagger.v3.oas.annotations.media.Schema;


public class MultipartUploadDomain extends BaseDomain {

    /**
     * 上传ID
     */
    @Schema(name = "上传ID")
    private String uploadId;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
}
