package com.pigx.engine.core.definition.exception;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/exception/PlatformException.class */
public class PlatformException extends Exception {
    public PlatformException() {
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }

    protected PlatformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
