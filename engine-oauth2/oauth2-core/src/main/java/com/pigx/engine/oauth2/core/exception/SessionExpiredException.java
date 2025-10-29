package com.pigx.engine.oauth2.core.exception;

import org.springframework.security.authentication.AccountStatusException;


public class SessionExpiredException extends AccountStatusException {

    public SessionExpiredException(String msg) {
        super(msg);
    }

    public SessionExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
