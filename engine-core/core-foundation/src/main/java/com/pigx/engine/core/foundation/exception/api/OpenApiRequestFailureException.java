package com.pigx.engine.core.foundation.exception.api;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/exception/api/OpenApiRequestFailureException.class */
public class OpenApiRequestFailureException extends PlatformRuntimeException {
    public OpenApiRequestFailureException() {
    }

    public OpenApiRequestFailureException(String message) {
        super(message);
    }

    public OpenApiRequestFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenApiRequestFailureException(Throwable cause) {
        super(cause);
    }

    protected OpenApiRequestFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return super.getFeedback();
    }
}
