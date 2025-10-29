package com.pigx.engine.cache.core.exception;

import com.pigx.engine.core.definition.exception.PlatformException;


public class StampParameterIllegalException extends PlatformException {

    public StampParameterIllegalException() {
    }

    public StampParameterIllegalException(String message) {
        super(message);
    }

    public StampParameterIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public StampParameterIllegalException(Throwable cause) {
        super(cause);
    }

    public StampParameterIllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
