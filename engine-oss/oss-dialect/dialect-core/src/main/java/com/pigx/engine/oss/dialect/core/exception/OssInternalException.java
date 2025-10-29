package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssInternalException extends PlatformRuntimeException {

    public OssInternalException() {
        super();
    }

    public OssInternalException(String message) {
        super(message);
    }

    public OssInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssInternalException(Throwable cause) {
        super(cause);
    }

    protected OssInternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_INTERNAL;
    }
}
