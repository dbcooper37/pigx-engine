package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssInvalidResponseException extends PlatformRuntimeException {

    public OssInvalidResponseException() {
        super();
    }

    public OssInvalidResponseException(String message) {
        super(message);
    }

    public OssInvalidResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssInvalidResponseException(Throwable cause) {
        super(cause);
    }

    protected OssInvalidResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_INVALID_RESPONSE;
    }
}
