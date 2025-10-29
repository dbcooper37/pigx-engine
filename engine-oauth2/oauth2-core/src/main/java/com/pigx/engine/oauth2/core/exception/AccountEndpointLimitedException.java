package com.pigx.engine.oauth2.core.exception;

import org.springframework.security.authentication.AccountStatusException;


public class AccountEndpointLimitedException extends AccountStatusException {

    public AccountEndpointLimitedException(String msg) {
        super(msg);
    }

    public AccountEndpointLimitedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
