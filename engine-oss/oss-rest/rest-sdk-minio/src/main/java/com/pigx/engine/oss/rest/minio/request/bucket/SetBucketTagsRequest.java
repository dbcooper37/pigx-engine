package com.pigx.engine.oss.rest.minio.request.bucket;

import com.pigx.engine.oss.rest.minio.definition.BucketRequest;
import io.minio.SetBucketTagsArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;


@Schema(name = "设置存储桶标签请求参数实体", title = "设置存储桶标签请求参数实体")
public class SetBucketTagsRequest extends BucketRequest<SetBucketTagsArgs.Builder, SetBucketTagsArgs> {

    @Schema(name = "存储桶标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "存储桶标签不能为空")
    private Map<String, String> tags;

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    @Override
    public void prepare(SetBucketTagsArgs.Builder builder) {
        if (ObjectUtils.isNotEmpty(getTags())) {
            builder.tags(getTags());
        }
        super.prepare(builder);
    }

    @Override
    public SetBucketTagsArgs.Builder getBuilder() {
        return SetBucketTagsArgs.builder();
    }
}
