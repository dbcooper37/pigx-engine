package com.pigx.engine.core.definition.function;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;


@FunctionalInterface
public interface ErrorCodeMapperBuilderCustomizer {

    /**
     * 自定义 ErrorCodeMapperBuilder
     *
     * @param builder 被扩展的 {@link ErrorCodeMapperBuilder}
     */
    void customize(ErrorCodeMapperBuilder builder);
}
