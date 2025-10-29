package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.definition.ObjectVersionRequest;
import io.minio.SetObjectTagsArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;


@Schema(name = "设置对象标签请求参数实体", title = "设置对象标签请求参数实体")
public class SetObjectTagsRequest extends ObjectVersionRequest<SetObjectTagsArgs.Builder, SetObjectTagsArgs> {

    @Schema(name = "对象标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "对象标签不能为空")
    private Map<String, String> tags;

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    @Override
    public void prepare(SetObjectTagsArgs.Builder builder) {
        builder.tags(getTags());
        super.prepare(builder);
    }

    @Override
    public SetObjectTagsArgs.Builder getBuilder() {
        return SetObjectTagsArgs.builder();
    }
}
