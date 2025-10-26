package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/exception/OAuth2CaptchaHasExpiredException.class */
public class OAuth2CaptchaHasExpiredException extends OAuth2CaptchaException {
    public OAuth2CaptchaHasExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2CaptchaHasExpiredException(String msg) {
        super(msg);
    }

    @Override // com.pigx.engine.oauth2.core.exception.OAuth2CaptchaException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return ErrorCodes.CAPTCHA_HAS_EXPIRED;
    }
}
