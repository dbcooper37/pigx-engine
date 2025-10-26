package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.identity.exception.PlatformAuthenticationException;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/UsernameAlreadyExistsException.class */
public class UsernameAlreadyExistsException extends PlatformAuthenticationException {
    public UsernameAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }

    @Override // com.pigx.engine.core.identity.exception.PlatformAuthenticationException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return ErrorCodes.USERNAME_ALREADY_EXISTS;
    }
}
