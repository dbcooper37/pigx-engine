package com.pigx.engine.oss.specification.arguments.base;

import com.pigx.engine.oss.specification.core.arguments.OssArguments;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;


public abstract class BaseArguments implements OssArguments {

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
}
