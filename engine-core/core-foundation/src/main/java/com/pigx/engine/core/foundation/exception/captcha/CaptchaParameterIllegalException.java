package com.pigx.engine.core.foundation.exception.captcha;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/exception/captcha/CaptchaParameterIllegalException.class */
public class CaptchaParameterIllegalException extends PlatformRuntimeException {
    public CaptchaParameterIllegalException() {
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

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return ErrorCodes.CAPTCHA_PARAMETER_ILLEGAL;
    }
}
