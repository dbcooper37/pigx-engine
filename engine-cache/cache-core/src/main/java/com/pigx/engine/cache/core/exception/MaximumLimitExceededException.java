package com.pigx.engine.cache.core.exception;

import com.pigx.engine.core.definition.exception.PlatformException;


public class MaximumLimitExceededException extends PlatformException {

    public MaximumLimitExceededException() {
        super();
    }

    public MaximumLimitExceededException(String message) {
        super(message);
    }

    public MaximumLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaximumLimitExceededException(Throwable cause) {
        super(cause);
    }

    protected MaximumLimitExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
