package com.pigx.engine.oss.specification.arguments.base;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;


public abstract class ObjectWriteArguments extends ObjectArguments {

    @Schema(name = "请求头信息")
    private Map<String, String> requestHeaders;

    @Schema(name = "对象元数据")
    private Map<String, String> metadata;

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
