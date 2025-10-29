package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.definition.ObjectConditionalReadRequest;
import com.pigx.engine.oss.rest.minio.definition.ObjectReadRequest;
import io.minio.StatObjectArgs;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "获取对象信息和元数据请求参数")
public class StatObjectRequest extends ObjectConditionalReadRequest<StatObjectArgs.Builder, StatObjectArgs> {

    public StatObjectRequest(ObjectReadRequest<StatObjectArgs.Builder, StatObjectArgs> request) {
        this.setExtraHeaders(request.getExtraHeaders());
        this.setExtraQueryParams(request.getExtraQueryParams());
        this.setBucketName(request.getBucketName());
        this.setRegion(request.getRegion());
        this.setObjectName(request.getObjectName());
        this.setVersionId(request.getVersionId());
        this.setServerSideEncryptionCustomerKey(request.getServerSideEncryptionCustomerKey());
    }

    @Override
    public StatObjectArgs.Builder getBuilder() {
        return StatObjectArgs.builder();
    }
}
