package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.definition.ObjectVersionRequest;
import io.minio.GetObjectRetentionArgs;


public class GetObjectRetentionRequest extends ObjectVersionRequest<GetObjectRetentionArgs.Builder, GetObjectRetentionArgs> {
    @Override
    public GetObjectRetentionArgs.Builder getBuilder() {
        return GetObjectRetentionArgs.builder();
    }
}
