package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.oss.specification.arguments.base.BaseArguments;
import io.minio.BaseArgs;
import org.apache.commons.collections4.MapUtils;


public abstract class ArgumentsToBaseConverter<S extends BaseArguments, T extends BaseArgs, B extends BaseArgs.Builder<B, T>> implements ArgumentsConverter<S, T, B> {

    @Override
    public void prepare(S arguments, B builder) {
        if (MapUtils.isNotEmpty(arguments.getExtraHeaders())) {
            builder.extraHeaders(arguments.getExtraHeaders());
        }

        if (MapUtils.isNotEmpty(arguments.getExtraQueryParams())) {
            builder.extraQueryParams(arguments.getExtraQueryParams());
        }
    }
}
