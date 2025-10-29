package com.pigx.engine.oss.rest.minio.request.bucket;

import com.pigx.engine.oss.dialect.minio.domain.VersioningConfigurationDomain;
import com.pigx.engine.oss.rest.minio.definition.BucketRequest;
import io.minio.SetBucketVersioningArgs;
import io.minio.messages.VersioningConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(name = "设置存储桶版本请求参数实体", title = "设置存储桶版本请求参数实体")
public class SetBucketVersioningRequest extends BucketRequest<SetBucketVersioningArgs.Builder, SetBucketVersioningArgs> {

    @Schema(name = "存储桶版本配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "存储桶版本配置不能为空")
    private VersioningConfigurationDomain config;

    public VersioningConfigurationDomain getConfig() {
        return config;
    }

    public void setConfig(VersioningConfigurationDomain config) {
        this.config = config;
    }

    @Override
    public void prepare(SetBucketVersioningArgs.Builder builder) {
        builder.config(new VersioningConfiguration(VersioningConfiguration.Status.valueOf(config.getStatus()), config.getMfaDelete()));
        super.prepare(builder);
    }

    @Override
    public SetBucketVersioningArgs.Builder getBuilder() {
        return SetBucketVersioningArgs.builder();
    }
}
