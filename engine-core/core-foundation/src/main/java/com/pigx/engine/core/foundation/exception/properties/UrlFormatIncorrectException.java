package com.pigx.engine.core.foundation.exception.properties;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class UrlFormatIncorrectException extends PlatformRuntimeException {

    public UrlFormatIncorrectException() {
        super();
    }

    public UrlFormatIncorrectException(String message) {
        super(message);
    }

    public UrlFormatIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlFormatIncorrectException(Throwable cause) {
        super(cause);
    }

    protected UrlFormatIncorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.URL_FORMAT_INCORRECT_EXCEPTION;
    }
}
