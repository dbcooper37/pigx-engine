package com.pigx.engine.oss.rest.minio.request.bucket;

import com.pigx.engine.oss.rest.minio.definition.BucketRequest;
import io.minio.DeleteObjectLockConfigurationArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "删除存储桶对象锁定配置请求参数实体", title = "删除存储桶对象锁定配置请求参数实体")
public class DeleteObjectLockConfigurationRequest extends BucketRequest<DeleteObjectLockConfigurationArgs.Builder, DeleteObjectLockConfigurationArgs> {
    @Override
    public DeleteObjectLockConfigurationArgs.Builder getBuilder() {
        return DeleteObjectLockConfigurationArgs.builder();
    }
}
