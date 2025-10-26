package com.pigx.engine.core.identity.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.exception.HerodotusException;
import org.springframework.security.core.AuthenticationException;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/exception/PlatformAuthenticationException.class */
public class PlatformAuthenticationException extends AuthenticationException implements HerodotusException {
    public PlatformAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PlatformAuthenticationException(String msg) {
        super(msg);
    }

    @Override // com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return ErrorCodes.INTERNAL_SERVER_ERROR;
    }

    @Override // com.pigx.engine.core.definition.exception.HerodotusException
    public Result<String> getResult() {
        Result<String> result = Result.failure(getFeedback());
        result.stackTrace(super.getStackTrace());
        result.detail(super.getMessage());
        return result;
    }
}
