package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.domain.Result;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/exception/AbstractRuntimeException.class */
public abstract class AbstractRuntimeException extends RuntimeException implements HerodotusException {
    public AbstractRuntimeException() {
    }

    public AbstractRuntimeException(String message) {
        super(message);
    }

    public AbstractRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractRuntimeException(Throwable cause) {
        super(cause);
    }

    protected AbstractRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Result<String> getResult() {
        Result<String> result = Result.failure(getFeedback());
        result.stackTrace(super.getStackTrace());
        result.detail(super.getMessage());
        return result;
    }
}
