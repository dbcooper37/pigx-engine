package com.pigx.engine.cache.core.exception;

import com.pigx.engine.core.definition.exception.PlatformException;


public class StampHasExpiredException extends PlatformException {

    public StampHasExpiredException() {
        super();
    }

    public StampHasExpiredException(String message) {
        super(message);
    }

    public StampHasExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public StampHasExpiredException(Throwable cause) {
        super(cause);
    }

    protected StampHasExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
