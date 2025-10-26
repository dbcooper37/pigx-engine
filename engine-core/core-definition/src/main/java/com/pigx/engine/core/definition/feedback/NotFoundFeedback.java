package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/feedback/NotFoundFeedback.class */
public class NotFoundFeedback extends Feedback {
    public NotFoundFeedback(String message) {
        super(message, 404);
    }
}
