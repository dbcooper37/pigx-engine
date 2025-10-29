package com.pigx.engine.core.definition.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;


public abstract non-sealed class Response<D, C> implements BaseDomain {

    @Schema(name = "响应代码")
    @JsonProperty("code")
    private C code;

    @Schema(name = "响应返回数据")
    @JsonProperty("data")
    private D data;

    @Schema(name = "响应返回信息")
    @JsonProperty("message")
    private String message;

    public C getCode() {
        return code;
    }

    public void setCode(C code) {
        this.code = code;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("data", data)
                .add("message", message)
                .toString();
    }
}
