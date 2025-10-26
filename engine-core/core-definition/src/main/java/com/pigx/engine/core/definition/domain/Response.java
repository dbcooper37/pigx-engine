package com.pigx.engine.core.definition.domain;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.view.datatables.DataTableUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/Response.class */
public abstract class Response<D, C> implements BaseDomain {

    @JsonProperty(SystemConstants.CODE)
    @Schema(name = "响应代码")
    private C code;

    @JsonProperty(DataTableUtils.DATA)
    @Schema(name = "响应返回数据")
    private D data;

    @JsonProperty("message")
    @Schema(name = "响应返回信息")
    private String message;

    public C getCode() {
        return this.code;
    }

    public void setCode(C code) {
        this.code = code;
    }

    public D getData() {
        return this.data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add(SystemConstants.CODE, this.code).add(DataTableUtils.DATA, this.data).add("message", this.message).toString();
    }
}
