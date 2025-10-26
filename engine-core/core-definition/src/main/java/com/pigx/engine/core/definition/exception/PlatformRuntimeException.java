package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/exception/PlatformRuntimeException.class */
public class PlatformRuntimeException extends AbstractRuntimeException {
    public PlatformRuntimeException() {
    }

    public PlatformRuntimeException(String message) {
        super(message);
    }

    public PlatformRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformRuntimeException(Throwable cause) {
        super(cause);
    }

    protected PlatformRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Feedback getFeedback() {
        return ErrorCodes.INTERNAL_SERVER_ERROR;
    }
}
