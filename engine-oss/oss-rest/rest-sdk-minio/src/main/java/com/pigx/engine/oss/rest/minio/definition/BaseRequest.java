package com.pigx.engine.oss.rest.minio.definition;

import io.minio.BaseArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;


public abstract class BaseRequest<B extends BaseArgs.Builder<B, A>, A extends BaseArgs> implements MinioRequestBuilder<B, A> {

    @Schema(name = "额外的请求头")
    private Map<String, String> extraHeaders;

    @Schema(name = "额外的Query参数")
    private Map<String, String> extraQueryParams;

    public Map<String, String> getExtraHeaders() {
        return extraHeaders;
    }

    public void setExtraHeaders(Map<String, String> extraHeaders) {
        this.extraHeaders = extraHeaders;
    }

    public Map<String, String> getExtraQueryParams() {
        return extraQueryParams;
    }

    public void setExtraQueryParams(Map<String, String> extraQueryParams) {
        this.extraQueryParams = extraQueryParams;
    }

    @Override
    public void prepare(B builder) {
        if (MapUtils.isNotEmpty(getExtraHeaders())) {
            builder.extraHeaders(getExtraHeaders());
        }

        if (MapUtils.isNotEmpty(getExtraQueryParams())) {
            builder.extraHeaders(getExtraQueryParams());
        }
    }
}
