package com.pigx.engine.core.foundation.exception.transaction;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/exception/transaction/TransactionalRollbackException.class */
public class TransactionalRollbackException extends PlatformRuntimeException {
    public TransactionalRollbackException() {
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

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return ErrorCodes.TRANSACTION_ROLLBACK;
    }
}
