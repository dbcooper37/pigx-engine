package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.identity.exception.PlatformAuthenticationException;


public class SocialCredentialsUserBindingFailedException extends PlatformAuthenticationException {

    public SocialCredentialsUserBindingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SocialCredentialsUserBindingFailedException(String msg) {
        super(msg);
    }
}
