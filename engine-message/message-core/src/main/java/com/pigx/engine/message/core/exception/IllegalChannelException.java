package com.pigx.engine.message.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.message.core.constants.MessageErrorCodes;


public class IllegalChannelException extends PlatformRuntimeException {

    public IllegalChannelException() {
        super();
    }

    public IllegalChannelException(String message) {
        super(message);
    }

    public IllegalChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalChannelException(Throwable cause) {
        super(cause);
    }

    protected IllegalChannelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return MessageErrorCodes.ILLEGAL_CHANNEL;
    }
}
