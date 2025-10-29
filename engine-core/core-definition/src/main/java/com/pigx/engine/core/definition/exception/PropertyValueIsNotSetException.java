package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;


public class PropertyValueIsNotSetException extends PlatformRuntimeException {

    public PropertyValueIsNotSetException() {
        super();
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

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.PROPERTY_VALUE_IS_NOT_SET_EXCEPTION;
    }
}
