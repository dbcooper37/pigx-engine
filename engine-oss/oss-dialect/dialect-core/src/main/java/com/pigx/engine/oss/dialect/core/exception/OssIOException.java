package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssIOException extends PlatformRuntimeException {

    public OssIOException() {
        super();
    }

    public OssIOException(String message) {
        super(message);
    }

    public OssIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssIOException(Throwable cause) {
        super(cause);
    }

    protected OssIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_IO;
    }
}
