package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.definition.ObjectVersionRequest;
import io.minio.DeleteObjectTagsArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "删除对象标签请求参数实体", title = "删除对象桶标签请求参数实体")
public class DeleteObjectTagsRequest extends ObjectVersionRequest<DeleteObjectTagsArgs.Builder, DeleteObjectTagsArgs> {
    @Override
    public DeleteObjectTagsArgs.Builder getBuilder() {
        return DeleteObjectTagsArgs.builder();
    }
}
