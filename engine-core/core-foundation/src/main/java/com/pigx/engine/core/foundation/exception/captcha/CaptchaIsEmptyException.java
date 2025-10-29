package com.pigx.engine.core.foundation.exception.captcha;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class CaptchaIsEmptyException extends PlatformRuntimeException {

    public CaptchaIsEmptyException() {
        super();
    }

    public CaptchaIsEmptyException(String message) {
        super(message);
    }

    public CaptchaIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaIsEmptyException(Throwable cause) {
        super(cause);
    }

    protected CaptchaIsEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.CAPTCHA_IS_EMPTY;
    }
}
