package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssNoSuchAlgorithmException extends PlatformRuntimeException {

    public OssNoSuchAlgorithmException() {
        super();
    }

    public OssNoSuchAlgorithmException(String message) {
        super(message);
    }

    public OssNoSuchAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssNoSuchAlgorithmException(Throwable cause) {
        super(cause);
    }

    protected OssNoSuchAlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_NO_SUCH_ALGORITHM;
    }
}
