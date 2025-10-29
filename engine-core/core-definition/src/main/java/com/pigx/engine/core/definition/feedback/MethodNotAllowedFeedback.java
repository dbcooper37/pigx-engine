package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class MethodNotAllowedFeedback extends Feedback {
    public MethodNotAllowedFeedback(String value) {
        super(value, HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}
