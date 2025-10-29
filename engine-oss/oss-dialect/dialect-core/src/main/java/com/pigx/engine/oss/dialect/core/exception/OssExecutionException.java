package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssExecutionException extends PlatformRuntimeException {

    public OssExecutionException() {
        super();
    }

    public OssExecutionException(String message) {
        super(message);
    }

    public OssExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssExecutionException(Throwable cause) {
        super(cause);
    }

    protected OssExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_EXECUTION;
    }
}
