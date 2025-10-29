package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class NotFoundFeedback extends Feedback {

    public NotFoundFeedback(String message) {
        super(message, HttpStatus.SC_NOT_FOUND);
    }
}
