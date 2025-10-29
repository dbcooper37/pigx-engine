package com.pigx.engine.oss.specification.arguments.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public abstract class BasePartArguments extends ObjectArguments {

    /**
     * 分片上传ID
     */
    @Schema(name = "分片上传ID")
    @NotBlank(message = "分片上传ID为空")
    private String uploadId;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
}
