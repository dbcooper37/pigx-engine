package com.pigx.engine.core.foundation.exception.feigin;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class FeignDecodeIOException extends PlatformRuntimeException {

    public FeignDecodeIOException() {
        super();
    }

    public FeignDecodeIOException(String message) {
        super(message);
    }

    public FeignDecodeIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeignDecodeIOException(Throwable cause) {
        super(cause);
    }

    protected FeignDecodeIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.FEIGN_DECODER_IO_EXCEPTION;
    }
}
