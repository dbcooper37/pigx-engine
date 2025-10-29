package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;


public class OAuth2CaptchaIsEmptyException extends OAuth2CaptchaException {

    public OAuth2CaptchaIsEmptyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2CaptchaIsEmptyException(String msg) {
        super(msg);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.CAPTCHA_IS_EMPTY;
    }
}
