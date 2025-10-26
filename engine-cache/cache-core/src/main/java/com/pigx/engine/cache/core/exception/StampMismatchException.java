package com.pigx.engine.core.exception;

import com.pigx.engine.core.definition.exception.PlatformException;

/* loaded from: cache-core-3.5.7.0.jar:cn/herodotus/engine/cache/core/exception/StampMismatchException.class */
public class StampMismatchException extends PlatformException {
    public StampMismatchException() {
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
