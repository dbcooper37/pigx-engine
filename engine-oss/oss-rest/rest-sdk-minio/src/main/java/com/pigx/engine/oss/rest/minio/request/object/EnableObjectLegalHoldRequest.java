package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.definition.ObjectVersionRequest;
import io.minio.EnableObjectLegalHoldArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "设置开启对象持有配置")
public class EnableObjectLegalHoldRequest extends ObjectVersionRequest<EnableObjectLegalHoldArgs.Builder, EnableObjectLegalHoldArgs> {
    @Override
    public EnableObjectLegalHoldArgs.Builder getBuilder() {
        return EnableObjectLegalHoldArgs.builder();
    }
}
