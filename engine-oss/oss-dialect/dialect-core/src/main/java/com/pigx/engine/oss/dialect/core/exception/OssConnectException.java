package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssConnectException extends PlatformRuntimeException {

    public OssConnectException() {
        super();
    }

    public OssConnectException(String message) {
        super(message);
    }

    public OssConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssConnectException(Throwable cause) {
        super(cause);
    }

    protected OssConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_CONNECTION;
    }
}
