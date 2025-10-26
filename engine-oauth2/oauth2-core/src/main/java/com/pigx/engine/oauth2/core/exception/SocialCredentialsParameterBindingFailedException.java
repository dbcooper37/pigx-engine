package com.pigx.engine.oauth2.core.exception;

import org.springframework.security.core.AuthenticationException;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/SocialCredentialsParameterBindingFailedException.class */
public class SocialCredentialsParameterBindingFailedException extends AuthenticationException {
    public SocialCredentialsParameterBindingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SocialCredentialsParameterBindingFailedException(String msg) {
        super(msg);
    }
}
