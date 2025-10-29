package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.oss.specification.arguments.base.ObjectReadArguments;
import io.minio.ObjectReadArgs;


public abstract class ArgumentsToObjectReadConverter<S extends ObjectReadArguments, T extends ObjectReadArgs, B extends ObjectReadArgs.Builder<B, T>> extends ArgumentsToObjectVersionConverter<S, T, B> {

}
