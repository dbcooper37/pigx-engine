package com.pigx.engine.core.foundation.exception.api;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class OpenApiRequestFailureException extends PlatformRuntimeException {

    public OpenApiRequestFailureException() {
        super();
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

    @Override
    public Feedback getFeedback() {
        return super.getFeedback();
    }
}
