package com.pigx.engine.core.foundation.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;


public class DiscoveredUnrecordedErrorException extends PlatformRuntimeException {

    public DiscoveredUnrecordedErrorException() {
        super();
    }

    public DiscoveredUnrecordedErrorException(String message) {
        super(message);
    }

    public DiscoveredUnrecordedErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscoveredUnrecordedErrorException(Throwable cause) {
        super(cause);
    }

    protected DiscoveredUnrecordedErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.DISCOVERED_UNRECORDED_ERROR_EXCEPTION;
    }
}
