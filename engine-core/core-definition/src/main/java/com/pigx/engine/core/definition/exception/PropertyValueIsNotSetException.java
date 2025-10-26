package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/exception/PropertyValueIsNotSetException.class */
public class PropertyValueIsNotSetException extends PlatformRuntimeException {
    public PropertyValueIsNotSetException() {
    }

    public PropertyValueIsNotSetException(String message) {
        super(message);
    }

    public PropertyValueIsNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyValueIsNotSetException(Throwable cause) {
        super(cause);
    }

    protected PropertyValueIsNotSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override // com.pigx.engine.core.definition.exception.PlatformRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Feedback getFeedback() {
        return ErrorCodes.PROPERTY_VALUE_IS_NOT_SET_EXCEPTION;
    }
}
