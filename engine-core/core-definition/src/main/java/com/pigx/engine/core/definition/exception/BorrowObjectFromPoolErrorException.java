package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;


public class BorrowObjectFromPoolErrorException extends PlatformRuntimeException {

    public BorrowObjectFromPoolErrorException() {
        super();
    }

    public BorrowObjectFromPoolErrorException(String message) {
        super(message);
    }

    public BorrowObjectFromPoolErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BorrowObjectFromPoolErrorException(Throwable cause) {
        super(cause);
    }

    protected BorrowObjectFromPoolErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION;
    }
}
