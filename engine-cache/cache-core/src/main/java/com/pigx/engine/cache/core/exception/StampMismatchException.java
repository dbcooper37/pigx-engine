package com.pigx.engine.cache.core.exception;

import com.pigx.engine.core.definition.exception.PlatformException;


public class StampMismatchException extends PlatformException {

    public StampMismatchException() {
        super();
    }

    public StampMismatchException(String message) {
        super(message);
    }

    public StampMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public StampMismatchException(Throwable cause) {
        super(cause);
    }

    protected StampMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
