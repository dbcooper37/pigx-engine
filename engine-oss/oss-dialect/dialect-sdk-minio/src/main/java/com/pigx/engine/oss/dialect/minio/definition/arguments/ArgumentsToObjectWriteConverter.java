package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.oss.specification.arguments.base.ObjectWriteArguments;
import io.minio.ObjectWriteArgs;
import org.apache.commons.collections4.MapUtils;


public abstract class ArgumentsToObjectWriteConverter<S extends ObjectWriteArguments, T extends ObjectWriteArgs, B extends ObjectWriteArgs.Builder<B, T>> extends ArgumentsToObjectConverter<S, T, B> {

    @Override
    public void prepare(S arguments, B builder) {

        if (MapUtils.isNotEmpty(arguments.getRequestHeaders())) {
            builder.headers(arguments.getRequestHeaders());
        }

        if (MapUtils.isNotEmpty(arguments.getMetadata())) {
            builder.userMetadata(arguments.getMetadata());
        }

        super.prepare(arguments, builder);
    }
}
