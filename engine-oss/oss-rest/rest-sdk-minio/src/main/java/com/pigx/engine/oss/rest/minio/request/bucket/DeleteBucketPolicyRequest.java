package com.pigx.engine.oss.rest.minio.request.bucket;

import com.pigx.engine.oss.rest.minio.definition.BucketRequest;
import io.minio.DeleteBucketPolicyArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "删除存储桶访问策略请求参数实体", title = "删除存储桶访问策略请求参数实体")
public class DeleteBucketPolicyRequest extends BucketRequest<DeleteBucketPolicyArgs.Builder, DeleteBucketPolicyArgs> {
    @Override
    public DeleteBucketPolicyArgs.Builder getBuilder() {
        return DeleteBucketPolicyArgs.builder();
    }
}
