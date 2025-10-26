package com.pigx.engine.web.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.web.core.constant.WebErrorCodes;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/exception/RepeatSubmissionException.class */
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

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return WebErrorCodes.REPEAT_SUBMISSION;
    }
}
