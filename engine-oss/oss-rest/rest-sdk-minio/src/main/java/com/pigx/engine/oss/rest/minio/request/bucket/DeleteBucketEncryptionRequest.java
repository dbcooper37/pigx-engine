package com.pigx.engine.oss.rest.minio.request.bucket;

import com.pigx.engine.oss.rest.minio.definition.BucketRequest;
import io.minio.DeleteBucketEncryptionArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "删除存储桶加密方式请求参数实体", title = "删除存储桶加密方式请求参数实体")
public class DeleteBucketEncryptionRequest extends BucketRequest<DeleteBucketEncryptionArgs.Builder, DeleteBucketEncryptionArgs> {
    @Override
    public DeleteBucketEncryptionArgs.Builder getBuilder() {
        return DeleteBucketEncryptionArgs.builder();
    }
}
