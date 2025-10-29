package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.oss.specification.arguments.base.PutObjectBaseArguments;
import io.minio.PutObjectBaseArgs;


public abstract class ArgumentsToPutObjectBaseConverter<S extends PutObjectBaseArguments, T extends PutObjectBaseArgs, B extends PutObjectBaseArgs.Builder<B, T>> extends ArgumentsToObjectWriteConverter<S, T, B> {
}
