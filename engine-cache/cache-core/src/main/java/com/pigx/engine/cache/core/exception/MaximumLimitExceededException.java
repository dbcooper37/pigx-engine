package com.pigx.engine.core.exception;

import com.pigx.engine.core.definition.exception.PlatformException;

/* loaded from: cache-core-3.5.7.0.jar:cn/herodotus/engine/cache/core/exception/MaximumLimitExceededException.class */
public class MaximumLimitExceededException extends PlatformException {
    public MaximumLimitExceededException() {
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
