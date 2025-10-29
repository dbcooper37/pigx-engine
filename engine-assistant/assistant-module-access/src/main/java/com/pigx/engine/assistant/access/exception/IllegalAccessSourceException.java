package com.pigx.engine.assistant.access.exception;

import com.pigx.engine.assistant.access.constant.AccessErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class IllegalAccessSourceException extends PlatformRuntimeException {

    public IllegalAccessSourceException() {
        super();
    }

    public IllegalAccessSourceException(String message) {
        super(message);
    }

    public IllegalAccessSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessSourceException(Throwable cause) {
        super(cause);
    }

    public IllegalAccessSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return AccessErrorCodes.ILLEGAL_ACCESS_SOURCE;
    }
}
