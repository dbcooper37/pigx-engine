package com.pigx.engine.web.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.web.core.constant.WebErrorCodes;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/exception/FrequentRequestsException.class */
public class FrequentRequestsException extends IllegalOperationException {
    public FrequentRequestsException() {
    }

    public FrequentRequestsException(String message) {
        super(message);
    }

    public FrequentRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrequentRequestsException(Throwable cause) {
        super(cause);
    }

    public FrequentRequestsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return WebErrorCodes.FREQUENT_REQUESTS;
    }
}
