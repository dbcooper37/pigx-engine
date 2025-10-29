package com.pigx.engine.oauth2.core.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;


public class OAuth2CaptchaMismatchException extends OAuth2CaptchaException {

    public OAuth2CaptchaMismatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2CaptchaMismatchException(String msg) {
        super(msg);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.CAPTCHA_MISMATCH;
    }
}
