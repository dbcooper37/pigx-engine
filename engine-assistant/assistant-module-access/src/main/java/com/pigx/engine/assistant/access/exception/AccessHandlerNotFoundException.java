package com.pigx.engine.assistant.access.exception;

import com.pigx.engine.assistant.access.constant.AccessErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class AccessHandlerNotFoundException extends PlatformRuntimeException {

    public AccessHandlerNotFoundException() {
        super();
    }

    public AccessHandlerNotFoundException(String message) {
        super(message);
    }

    public AccessHandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessHandlerNotFoundException(Throwable cause) {
        super(cause);
    }

    public AccessHandlerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return AccessErrorCodes.ACCESS_HANDLER_NOT_FOUND;
    }
}
