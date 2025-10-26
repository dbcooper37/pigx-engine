package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.identity.exception.PlatformAuthenticationException;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/SocialCredentialsUserBindingFailedException.class */
public class SocialCredentialsUserBindingFailedException extends PlatformAuthenticationException {
    public SocialCredentialsUserBindingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SocialCredentialsUserBindingFailedException(String msg) {
        super(msg);
    }
}
