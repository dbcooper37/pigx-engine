package com.pigx.engine.core.definition.domain;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(name = "响应错误详情", description = "为兼容Validation而增加的Validation错误信息实体")
/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/Error.class */
public class Error implements Serializable {

    @Schema(name = "Exception完整信息", type = "string")
    private String detail;

    @Schema(name = "额外的错误信息，目前主要是Validation的Message")
    private String message;

    @Schema(name = "额外的错误代码，目前主要是Validation的Code")
    private String code;

    @Schema(name = "额外的错误字段，目前主要是Validation的Field")
    private String field;

    @Schema(name = "错误堆栈信息")
    private StackTraceElement[] stackTrace;

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("detail", this.detail).add("message", this.message).add(SystemConstants.CODE, this.code).add("field", this.field).toString();
    }
}
