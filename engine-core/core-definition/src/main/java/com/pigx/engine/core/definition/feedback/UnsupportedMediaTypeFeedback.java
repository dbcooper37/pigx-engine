package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;


public class UnsupportedMediaTypeFeedback extends Feedback {
    public UnsupportedMediaTypeFeedback(String value) {
        super(value, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }
}
