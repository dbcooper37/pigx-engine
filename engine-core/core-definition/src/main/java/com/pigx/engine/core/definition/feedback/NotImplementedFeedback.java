package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class NotImplementedFeedback extends Feedback {
    public NotImplementedFeedback(String value) {
        super(value, HttpStatus.SC_NOT_IMPLEMENTED);
    }
}
