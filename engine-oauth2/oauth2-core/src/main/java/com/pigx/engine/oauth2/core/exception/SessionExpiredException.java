package com.pigx.engine.oauth2.core.exception;

import org.springframework.security.authentication.AccountStatusException;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/SessionExpiredException.class */
public class SessionExpiredException extends AccountStatusException {
    public SessionExpiredException(String msg) {
        super(msg);
    }

    public SessionExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
