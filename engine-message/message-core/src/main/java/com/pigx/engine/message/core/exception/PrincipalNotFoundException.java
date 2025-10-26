package com.pigx.engine.message.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.message.core.constants.MessageErrorCodes;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/exception/PrincipalNotFoundException.class */
public class PrincipalNotFoundException extends PlatformRuntimeException {
    public PrincipalNotFoundException() {
    }

    public PrincipalNotFoundException(String message) {
        super(message);
    }

    public PrincipalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrincipalNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PrincipalNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return MessageErrorCodes.PRINCIPAL_NOT_FOUND;
    }
}
