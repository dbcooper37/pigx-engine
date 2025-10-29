package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class NotAcceptableFeedback extends Feedback {
    public NotAcceptableFeedback(String value) {
        super(value, HttpStatus.SC_NOT_ACCEPTABLE);
    }
}
