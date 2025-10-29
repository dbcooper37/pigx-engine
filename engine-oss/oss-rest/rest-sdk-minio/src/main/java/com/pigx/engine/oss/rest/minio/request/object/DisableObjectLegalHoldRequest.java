package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.definition.ObjectVersionRequest;
import io.minio.DisableObjectLegalHoldArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "设置关闭对象持有配置")
public class DisableObjectLegalHoldRequest extends ObjectVersionRequest<DisableObjectLegalHoldArgs.Builder, DisableObjectLegalHoldArgs> {
    @Override
    public DisableObjectLegalHoldArgs.Builder getBuilder() {
        return DisableObjectLegalHoldArgs.builder();
    }
}
