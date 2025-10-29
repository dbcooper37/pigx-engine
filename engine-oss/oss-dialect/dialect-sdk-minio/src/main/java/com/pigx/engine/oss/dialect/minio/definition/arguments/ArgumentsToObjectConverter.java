package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.oss.specification.arguments.base.ObjectArguments;
import io.minio.ObjectArgs;


public abstract class ArgumentsToObjectConverter<S extends ObjectArguments, T extends ObjectArgs, B extends ObjectArgs.Builder<B, T>> extends ArgumentsToBucketConverter<S, T, B> {
    @Override
    public void prepare(S arguments, B builder) {
        builder.object(arguments.getObjectName());
        super.prepare(arguments, builder);
    }
}
