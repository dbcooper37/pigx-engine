package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssInterruptedException extends PlatformRuntimeException {

    public OssInterruptedException() {
        super();
    }

    public OssInterruptedException(String message) {
        super(message);
    }

    public OssInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssInterruptedException(Throwable cause) {
        super(cause);
    }

    protected OssInterruptedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_INTERRUPTED;
    }
}
