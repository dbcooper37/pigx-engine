package com.pigx.engine.oss.rest.minio.request.bucket;

import com.pigx.engine.oss.rest.minio.definition.BucketRequest;
import io.minio.DeleteBucketTagsArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "删除存储桶标签请求参数实体", title = "删除存储桶标签请求参数实体")
public class DeleteBucketTagsRequest extends BucketRequest<DeleteBucketTagsArgs.Builder, DeleteBucketTagsArgs> {

    @Override
    public DeleteBucketTagsArgs.Builder getBuilder() {
        return DeleteBucketTagsArgs.builder();
    }
}
