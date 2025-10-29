package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToObjectConditionalReadConverter;
import com.pigx.engine.oss.specification.arguments.object.GetObjectArguments;
import io.minio.GetObjectArgs;


public class ArgumentsToGetObjectArgsConverter extends ArgumentsToObjectConditionalReadConverter<GetObjectArguments, GetObjectArgs, GetObjectArgs.Builder> {

    @Override
    public GetObjectArgs.Builder getBuilder() {
        return GetObjectArgs.builder();
    }
}
