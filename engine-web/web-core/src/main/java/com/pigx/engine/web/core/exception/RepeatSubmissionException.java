package com.pigx.engine.web.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.web.core.constant.WebErrorCodes;


public class RepeatSubmissionException extends IllegalOperationException {

    public RepeatSubmissionException() {
    }

    public RepeatSubmissionException(String message) {
        super(message);
    }

    public RepeatSubmissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatSubmissionException(Throwable cause) {
        super(cause);
    }

    public RepeatSubmissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return WebErrorCodes.REPEAT_SUBMISSION;
    }
}
