package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class CustomizeFeedback extends Feedback {
    public CustomizeFeedback(String value, int custom) {
        super(value, HttpStatus.SC_INTERNAL_SERVER_ERROR, custom);
    }
}
