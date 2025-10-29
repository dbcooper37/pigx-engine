package com.pigx.engine.oauth2.core.exception;

import org.springframework.security.core.AuthenticationException;


public class SocialCredentialsParameterBindingFailedException extends AuthenticationException {

    public SocialCredentialsParameterBindingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SocialCredentialsParameterBindingFailedException(String msg) {
        super(msg);
    }
}
