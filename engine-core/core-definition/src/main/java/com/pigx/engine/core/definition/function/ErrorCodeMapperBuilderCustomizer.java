package com.pigx.engine.core.definition.function;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;

@FunctionalInterface
/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/function/ErrorCodeMapperBuilderCustomizer.class */
public interface ErrorCodeMapperBuilderCustomizer {
    void customize(ErrorCodeMapperBuilder builder);
}
