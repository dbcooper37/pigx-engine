package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.exception.HerodotusException;
import org.springframework.security.authentication.AccountStatusException;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/OAuth2CaptchaException.class */
public class OAuth2CaptchaException extends AccountStatusException implements HerodotusException {
    public OAuth2CaptchaException(String msg) {
        super(msg);
    }

    public OAuth2CaptchaException(String msg, Throwable cause) {
        super(msg, cause);
    }

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
