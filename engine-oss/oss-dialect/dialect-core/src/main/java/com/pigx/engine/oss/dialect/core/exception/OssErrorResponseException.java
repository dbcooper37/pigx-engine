package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssErrorResponseException extends PlatformRuntimeException {

    public OssErrorResponseException() {
        super();
    }

    public OssErrorResponseException(String message) {
        super(message);
    }

    public OssErrorResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssErrorResponseException(Throwable cause) {
        super(cause);
    }

    protected OssErrorResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_ERROR_RESPONSE;
    }
}
