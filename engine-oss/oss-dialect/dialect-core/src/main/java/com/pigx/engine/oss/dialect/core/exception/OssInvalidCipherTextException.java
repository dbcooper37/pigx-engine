package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssInvalidCipherTextException extends PlatformRuntimeException {

    public OssInvalidCipherTextException() {
        super();
    }

    public OssInvalidCipherTextException(String message) {
        super(message);
    }

    public OssInvalidCipherTextException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssInvalidCipherTextException(Throwable cause) {
        super(cause);
    }

    protected OssInvalidCipherTextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_INVALID_CIPHER_TEXT;
    }
}
