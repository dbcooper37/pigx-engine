package com.pigx.engine.oss.specification.arguments.base;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.RegexPool;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;


public abstract class BucketArguments extends BaseArguments {

    @Schema(name = "存储桶名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "存储桶名称不能为空")
    @Length(min = 3, max = 62, message = "存储桶名称不能少于3个字符，不能大于63个字符")
    @Pattern(regexp = RegexPool.DNS_COMPATIBLE, message = "存储桶名称无法与DNS兼容")
    private String bucketName;
    @Schema(name = "存储区域", description = "仅在Minio环境下使用，Amazon S3 已废弃")
    private String region;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bucketName", bucketName)
                .add("region", region)
                .toString();
    }
}
