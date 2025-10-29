package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class OkFeedback extends Feedback {
    public OkFeedback(String value) {
        super(value, HttpStatus.SC_OK);
    }
}
