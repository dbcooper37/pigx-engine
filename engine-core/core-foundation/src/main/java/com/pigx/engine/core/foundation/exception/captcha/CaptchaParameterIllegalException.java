package com.pigx.engine.core.foundation.exception.captcha;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class CaptchaParameterIllegalException extends PlatformRuntimeException {

    public CaptchaParameterIllegalException() {
        super();
    }

    public CaptchaParameterIllegalException(String message) {
        super(message);
    }

    public CaptchaParameterIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaParameterIllegalException(Throwable cause) {
        super(cause);
    }

    protected CaptchaParameterIllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.CAPTCHA_PARAMETER_ILLEGAL;
    }
}
