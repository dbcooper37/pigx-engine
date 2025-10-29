package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class UnauthorizedFeedback extends Feedback {
    public UnauthorizedFeedback(String value) {
        super(value, HttpStatus.SC_UNAUTHORIZED);
    }
}
