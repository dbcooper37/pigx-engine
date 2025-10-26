package com.pigx.engine.core.definition.domain;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.view.datatables.DataTableUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Schema(name = "统一响应返回实体", description = "所有Rest接口统一返回的实体定义", example = "new Result<T>().ok().message(\"XXX\")")
/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/Result.class */
public class Result<T> extends Response<T, Integer> {

    @Schema(name = "请求路径")
    private String path;

    @Schema(name = "http状态码")
    private int status;

    @Schema(name = "链路追踪TraceId")
    private String traceId;

    @Schema(name = "响应时间戳", pattern = SystemConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT)
    private final Date timestamp = new Date();

    @Schema(name = "校验错误信息")
    private final Error error = new Error();

    private static <T> Result<T> create(String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
        Result<T> result = new Result<>();
        if (StringUtils.isNotBlank(message)) {
            result.message(message);
        }
        if (StringUtils.isNotBlank(detail)) {
            result.detail(detail);
        }
        result.code(code);
        result.status(status);
        if (ObjectUtils.isNotEmpty(data)) {
            result.data(data);
        }
        if (ArrayUtils.isNotEmpty(stackTrace)) {
            result.stackTrace(stackTrace);
        }
        return result;
    }

    public static <T> Result<T> success(String message, int code, int status, T data) {
        return create(message, null, code, status, data, null);
    }

    public static <T> Result<T> success(String message, int code, T data) {
        return success(message, code, 200, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return success(message, ErrorCodes.OK.getSequence(), data);
    }

    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    public static <T> Result<T> success() {
        return success("操作成功！");
    }

    public static <T> Result<T> content(T data) {
        return success("操作成功！", data);
    }

    public static <T> Result<T> failure(String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
        return create(message, detail, code, status, data, stackTrace);
    }

    public static <T> Result<T> failure(String message, String detail, int code, int status, T data) {
        return failure(message, detail, code, status, data, null);
    }

    public static <T> Result<T> failure(String message, int code, int status, T data) {
        return failure(message, message, code, status, data);
    }

    public static <T> Result<T> failure(String message, String detail, int code, T data) {
        return failure(message, detail, code, 500, data);
    }

    public static <T> Result<T> failure(String message, int code, T data) {
        return failure(message, message, code, data);
    }

    public static Result<String> failure(Feedback feedback) {
        return failure(feedback, SymbolConstants.BLANK);
    }

    public static <T> Result<T> failure(Feedback feedback, T data) {
        Feedback result = ObjectUtils.isNotEmpty(feedback) ? feedback : ErrorCodes.DISCOVERED_UNRECORDED_ERROR_EXCEPTION;
        Integer code = ErrorCodeMapper.get(result);
        return failure(feedback.getMessage(), code.intValue(), feedback.getStatus(), data);
    }

    public static <T> Result<T> failure(String message, T data) {
        return failure(message, ErrorCodes.INTERNAL_SERVER_ERROR.getSequence(), data);
    }

    public static <T> Result<T> failure(String message) {
        return failure(message, null);
    }

    public static <T> Result<T> failure() {
        return failure("操作失败！");
    }

    public static <T> Result<T> empty(String message, int code, int status) {
        return create(message, null, code, status, null, null);
    }

    public static <T> Result<T> empty(String message, int code) {
        return empty(message, code, ErrorCodes.OK.getStatus());
    }

    public static <T> Result<T> empty(Feedback feedback) {
        int code = ErrorCodeMapper.get(feedback).intValue();
        return empty(feedback.getMessage(), code, feedback.getStatus());
    }

    public static <T> Result<T> empty(String message) {
        return empty(message, ErrorCodes.OK.getSequence());
    }

    public static <T> Result<T> empty() {
        return empty("未查询到相关内容！");
    }

    public String getPath() {
        return this.path;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public Error getError() {
        return this.error;
    }

    public Result<T> code(int code) {
        setCode(Integer.valueOf(code));
        return this;
    }

    public Result<T> message(String message) {
        setMessage(message);
        return this;
    }

    public Result<T> data(T data) {
        setData(data);
        return this;
    }

    public Result<T> path(String path) {
        this.path = path;
        return this;
    }

    public Result<T> type(Feedback feedback) {
        setCode(ErrorCodeMapper.get(feedback));
        setMessage(feedback.getMessage());
        this.status = feedback.getStatus();
        return this;
    }

    public Result<T> status(int httpStatus) {
        this.status = httpStatus;
        return this;
    }

    public Result<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public Result<T> stackTrace(StackTraceElement[] stackTrace) {
        this.error.setStackTrace(stackTrace);
        return this;
    }

    public Result<T> detail(String detail) {
        this.error.setDetail(detail);
        return this;
    }

    public Result<T> validation(String message, String code, String field) {
        this.error.setMessage(message);
        this.error.setCode(code);
        this.error.setField(field);
        return this;
    }

    private Map<String, Object> createModel() {
        Map<String, Object> result = new HashMap<>(8);
        result.put(SystemConstants.CODE, getCode());
        result.put("message", getMessage());
        result.put("path", this.path);
        result.put("status", Integer.valueOf(this.status));
        result.put("timestamp", this.timestamp);
        return result;
    }

    public Map<String, Object> toModel() {
        Map<String, Object> result = createModel();
        result.put(DataTableUtils.DATA, getData());
        result.put("error", this.error);
        return result;
    }

    public Map<String, Object> toErrorModel() {
        Map<String, Object> result = createModel();
        result.put("exception", getData());
        result.put("error", ObjectUtils.isNotEmpty(getError()) ? getError().getDetail() : SymbolConstants.BLANK);
        return result;
    }

    @Override // com.pigx.engine.core.definition.domain.Response
    public String toString() {
        return MoreObjects.toStringHelper(this).add(SystemConstants.CODE, getCode()).add("message", getMessage()).add("path", this.path).add(DataTableUtils.DATA, getData()).add("status", this.status).add("timestamp", this.timestamp).add("error", this.error).toString();
    }
}
