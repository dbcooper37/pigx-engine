package com.pigx.engine.core.exception;

import com.pigx.engine.core.definition.exception.PlatformException;

/* loaded from: cache-core-3.5.7.0.jar:cn/herodotus/engine/cache/core/exception/StampHasExpiredException.class */
public class StampHasExpiredException extends PlatformException {
    public StampHasExpiredException() {
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
