package com.pigx.engine.core.foundation.exception.captcha;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class CaptchaHandlerNotExistException extends PlatformRuntimeException {

    public CaptchaHandlerNotExistException() {
        super();
    }

    public CaptchaHandlerNotExistException(String message) {
        super(message);
    }

    public CaptchaHandlerNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaHandlerNotExistException(Throwable cause) {
        super(cause);
    }

    protected CaptchaHandlerNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.CAPTCHA_HANDLER_NOT_EXIST;
    }
}
