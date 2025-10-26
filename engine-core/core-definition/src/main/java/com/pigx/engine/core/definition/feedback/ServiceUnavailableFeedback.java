package com.pigx.engine.core.definition.feedback;

import com.pigx.engine.core.definition.domain.Feedback;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/feedback/ServiceUnavailableFeedback.class */
public class ServiceUnavailableFeedback extends Feedback {
    public ServiceUnavailableFeedback(String value) {
        super(value, 503);
    }
}
