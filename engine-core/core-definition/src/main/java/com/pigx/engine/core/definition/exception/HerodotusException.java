package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.domain.Result;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/exception/HerodotusException.class */
public interface HerodotusException {
    Feedback getFeedback();

    Result<String> getResult();
}
