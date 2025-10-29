package com.pigx.engine.oss.rest.minio.definition;

import com.pigx.engine.core.definition.constant.RegexPool;
import io.minio.BucketArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;


public abstract class BucketRequest<B extends BucketArgs.Builder<B, A>, A extends BucketArgs> extends BaseRequest<B, A> {

    @Schema(name = "存储桶名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "存储桶名称不能为空")
    @Length(min = 3, max = 62, message = "存储桶名称不能少于3个字符，不能大于63个字符")
    @Pattern(regexp = RegexPool.DNS_COMPATIBLE, message = "存储桶名称无法与DNS兼容")
    private String bucketName;
    @Schema(name = "存储区域")
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
    public void prepare(B builder) {
        builder.bucket(getBucketName());
        if (StringUtils.isNotBlank(getRegion())) {
            builder.region(getRegion());
        }

        super.prepare(builder);
    }
}
