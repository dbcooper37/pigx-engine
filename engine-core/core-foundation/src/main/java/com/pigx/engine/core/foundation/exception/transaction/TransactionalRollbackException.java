package com.pigx.engine.core.foundation.exception.transaction;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class TransactionalRollbackException extends PlatformRuntimeException {

    public TransactionalRollbackException() {
        super();
    }

    public TransactionalRollbackException(String message) {
        super(message);
    }

    public TransactionalRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalRollbackException(Throwable cause) {
        super(cause);
    }

    protected TransactionalRollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.TRANSACTION_ROLLBACK;
    }
}
