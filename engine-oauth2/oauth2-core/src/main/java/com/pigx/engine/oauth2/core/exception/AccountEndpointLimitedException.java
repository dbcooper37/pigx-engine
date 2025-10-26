package com.pigx.engine.oauth2.core.exception;

import org.springframework.security.authentication.AccountStatusException;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/AccountEndpointLimitedException.class */
public class AccountEndpointLimitedException extends AccountStatusException {
    public AccountEndpointLimitedException(String msg) {
        super(msg);
    }

    public AccountEndpointLimitedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
