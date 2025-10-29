package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssInsufficientDataException extends PlatformRuntimeException {

    public OssInsufficientDataException() {
        super();
    }

    public OssInsufficientDataException(String message) {
        super(message);
    }

    public OssInsufficientDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssInsufficientDataException(Throwable cause) {
        super(cause);
    }

    protected OssInsufficientDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_INSUFFICIENT_DATA;
    }
}
