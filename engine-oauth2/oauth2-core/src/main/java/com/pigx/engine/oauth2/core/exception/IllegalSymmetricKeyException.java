package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.identity.exception.PlatformAuthenticationException;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/IllegalSymmetricKeyException.class */
public class IllegalSymmetricKeyException extends PlatformAuthenticationException {
    public IllegalSymmetricKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalSymmetricKeyException(String msg) {
        super(msg);
    }

    @Override // com.pigx.engine.core.identity.exception.PlatformAuthenticationException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return ErrorCodes.ILLEGAL_SYMMETRIC_KEY;
    }
}
