package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssInvalidKeyException extends PlatformRuntimeException {

    public OssInvalidKeyException() {
        super();
    }

    public OssInvalidKeyException(String message) {
        super(message);
    }

    public OssInvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssInvalidKeyException(Throwable cause) {
        super(cause);
    }

    protected OssInvalidKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_INVALID_KEY;
    }
}
