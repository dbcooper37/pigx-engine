package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.dialect.minio.converter.retention.DomainToRetentionConverter;
import com.pigx.engine.oss.dialect.minio.domain.RetentionDomain;
import com.pigx.engine.oss.rest.minio.definition.ObjectVersionRequest;
import io.minio.SetObjectRetentionArgs;
import io.minio.messages.Retention;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


@Schema(name = "设置对象保留请求参数实体", title = "设置对象保留请求参数实体")
public class SetObjectRetentionRequest extends ObjectVersionRequest<SetObjectRetentionArgs.Builder, SetObjectRetentionArgs> {

    private final Converter<RetentionDomain, Retention> toRetention = new DomainToRetentionConverter();

    @Schema(name = "保留配置", requiredMode = Schema.RequiredMode.REQUIRED, description = "既然是设置操作那么设置值就不能为空")
    private RetentionDomain retention;

    @Schema(name = "使用Governance模式")
    private Boolean bypassGovernanceMode;

    public RetentionDomain getRetention() {
        return retention;
    }

    public void setRetention(RetentionDomain retention) {
        this.retention = retention;
    }

    public Boolean getBypassGovernanceMode() {
        return bypassGovernanceMode;
    }

    public void setBypassGovernanceMode(Boolean bypassGovernanceMode) {
        this.bypassGovernanceMode = bypassGovernanceMode;
    }

    @Override
    public void prepare(SetObjectRetentionArgs.Builder builder) {
        builder.config(toRetention.convert(getRetention()));

        if (ObjectUtils.isNotEmpty(getBypassGovernanceMode())) {
            builder.bypassGovernanceMode(getBypassGovernanceMode());
        }
        super.prepare(builder);
    }

    @Override
    public SetObjectRetentionArgs.Builder getBuilder() {
        return SetObjectRetentionArgs.builder();
    }
}
