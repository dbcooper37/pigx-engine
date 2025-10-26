package com.pigx.engine.assistant.access.exception;

import com.pigx.engine.assistant.access.constant.AccessErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/exception/IllegalAccessArgumentException.class */
public class IllegalAccessArgumentException extends PlatformRuntimeException {
    public IllegalAccessArgumentException() {
    }

    public IllegalAccessArgumentException(String message) {
        super(message);
    }

    public IllegalAccessArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessArgumentException(Throwable cause) {
        super(cause);
    }

    public IllegalAccessArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return AccessErrorCodes.ILLEGAL_ACCESS_ARGUMENT;
    }
}
