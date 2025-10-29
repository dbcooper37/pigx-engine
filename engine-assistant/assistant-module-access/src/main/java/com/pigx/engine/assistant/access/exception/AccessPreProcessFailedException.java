package com.pigx.engine.assistant.access.exception;

import com.pigx.engine.assistant.access.constant.AccessErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class AccessPreProcessFailedException extends PlatformRuntimeException {

    public AccessPreProcessFailedException() {
    }

    public AccessPreProcessFailedException(String message) {
        super(message);
    }

    public AccessPreProcessFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessPreProcessFailedException(Throwable cause) {
        super(cause);
    }

    public AccessPreProcessFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return AccessErrorCodes.ACCESS_PRE_PROCESS_FAILED_EXCEPTION;
    }
}
