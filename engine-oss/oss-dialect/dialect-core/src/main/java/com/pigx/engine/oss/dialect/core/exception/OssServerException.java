package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssServerException extends PlatformRuntimeException {
    public OssServerException() {
        super();
    }

    public OssServerException(String message) {
        super(message);
    }

    public OssServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssServerException(Throwable cause) {
        super(cause);
    }

    protected OssServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_SERVER;
    }
}
