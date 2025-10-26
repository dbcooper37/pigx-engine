package com.pigx.engine.web.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.web.core.constant.WebErrorCodes;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/exception/SessionInvalidException.class */
public class SessionInvalidException extends PlatformRuntimeException {
    public SessionInvalidException() {
    }

    public SessionInvalidException(String message) {
        super(message);
    }

    public SessionInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionInvalidException(Throwable cause) {
        super(cause);
    }

    public SessionInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return WebErrorCodes.SESSION_INVALID;
    }
}
