package com.pigx.engine.oss.rest.minio.request.domain;

import com.pigx.engine.oss.rest.minio.definition.ObjectConditionalReadRequest;
import io.minio.ComposeSource;
import io.minio.CopySource;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;


@Schema(name = "组合对象源请求参数")
public class ComposeSourceRequest extends ObjectConditionalReadRequest<ComposeSource.Builder, ComposeSource> {

    private Long objectSize = null;
    private Map<String, String> headers = null;

    public ComposeSourceRequest(ObjectConditionalReadRequest<CopySource.Builder, CopySource> request) {
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

    public Long getObjectSize() {
        return objectSize;
    }

    public void setObjectSize(Long objectSize) {
        this.objectSize = objectSize;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public ComposeSource.Builder getBuilder() {
        return ComposeSource.builder();
    }
}
