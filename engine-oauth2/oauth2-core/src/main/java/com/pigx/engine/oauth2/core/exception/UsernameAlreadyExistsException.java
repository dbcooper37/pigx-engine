package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.identity.exception.PlatformAuthenticationException;


public class UsernameAlreadyExistsException extends PlatformAuthenticationException {

    public UsernameAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.USERNAME_ALREADY_EXISTS;
    }
}
