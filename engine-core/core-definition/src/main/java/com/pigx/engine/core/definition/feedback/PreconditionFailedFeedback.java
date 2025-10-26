package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/feedback/PreconditionFailedFeedback.class */
public class PreconditionFailedFeedback extends Feedback {
    public PreconditionFailedFeedback(String value) {
        super(value, 412);
    }
}
