package com.pigx.engine.web.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.web.core.constant.WebErrorCodes;


public class FrequentRequestsException extends IllegalOperationException {

    public FrequentRequestsException() {
    }

    public FrequentRequestsException(String message) {
        super(message);
    }

    public FrequentRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrequentRequestsException(Throwable cause) {
        super(cause);
    }

    public FrequentRequestsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return WebErrorCodes.FREQUENT_REQUESTS;
    }
}
