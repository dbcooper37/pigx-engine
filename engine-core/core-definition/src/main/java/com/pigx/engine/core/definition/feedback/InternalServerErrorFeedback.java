package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/feedback/InternalServerErrorFeedback.class */
public class InternalServerErrorFeedback extends Feedback {
    public InternalServerErrorFeedback(String value) {
        super(value, 500);
    }
}
