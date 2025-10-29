package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class NoContentFeedback extends Feedback {
    public NoContentFeedback(String value) {
        super(value, HttpStatus.SC_NO_CONTENT);
    }
}
