package com.pigx.engine.oss.rest.minio.request.domain;

import com.pigx.engine.oss.rest.minio.definition.ObjectConditionalReadRequest;
import io.minio.ComposeSource;
import io.minio.CopySource;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "复制对象源请求参数")
public class CopySourceRequest extends ObjectConditionalReadRequest<CopySource.Builder, CopySource> {

    public CopySourceRequest(ObjectConditionalReadRequest<ComposeSource.Builder, ComposeSource> request) {
        this.setExtraHeaders(request.getExtraHeaders());
        this.setExtraQueryParams(request.getExtraQueryParams());
        this.setBucketName(request.getBucketName());
        this.setRegion(request.getRegion());
        this.setObjectName(request.getObjectName());
        this.setVersionId(request.getVersionId());
        this.setServerSideEncryptionCustomerKey(request.getServerSideEncryptionCustomerKey());
        this.setOffset(request.getOffset());
        this.setLength(request.getLength());
        this.setMatchETag(request.getMatchETag());
        this.setNotMatchETag(request.getNotMatchETag());
        this.setModifiedSince(request.getModifiedSince());
        this.setUnmodifiedSince(request.getUnmodifiedSince());
    }

    @Override
    public CopySource.Builder getBuilder() {
        return CopySource.builder();
    }
}
