package com.pigx.engine.oss.rest.minio.request.bucket;

import com.pigx.engine.oss.dialect.minio.converter.retention.DomainToObjectLockConfigurationConverter;
import com.pigx.engine.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import com.pigx.engine.oss.rest.minio.definition.BucketRequest;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.messages.ObjectLockConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;


@Schema(name = "设置存储桶对象锁定配置请求参数实体", title = "设置存储桶对象锁定配置请求参数实体")
public class SetObjectLockConfigurationRequest extends BucketRequest<SetObjectLockConfigurationArgs.Builder, SetObjectLockConfigurationArgs> {

    private final Converter<ObjectLockConfigurationDomain, ObjectLockConfiguration> requestTo = new DomainToObjectLockConfigurationConverter();

    @Schema(name = "对象锁定配置", requiredMode = Schema.RequiredMode.REQUIRED, description = "既然是设置操作那么设置的值就不能为空")
    @NotNull(message = "对象锁定配置信息不能为空")
    private ObjectLockConfigurationDomain objectLock;

    public ObjectLockConfigurationDomain getObjectLock() {
        return objectLock;
    }

    public void setObjectLock(ObjectLockConfigurationDomain objectLock) {
        this.objectLock = objectLock;
    }

    @Override
    public void prepare(SetObjectLockConfigurationArgs.Builder builder) {
        // 既然是设置操作那么设置的值就不能为空
        builder.config(requestTo.convert(getObjectLock()));
        super.prepare(builder);
    }

    @Override
    public SetObjectLockConfigurationArgs.Builder getBuilder() {
        return SetObjectLockConfigurationArgs.builder();
    }
}
