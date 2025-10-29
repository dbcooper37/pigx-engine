package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class PreconditionFailedFeedback extends Feedback {
    public PreconditionFailedFeedback(String value) {
        super(value, HttpStatus.SC_PRECONDITION_FAILED);
    }
}
