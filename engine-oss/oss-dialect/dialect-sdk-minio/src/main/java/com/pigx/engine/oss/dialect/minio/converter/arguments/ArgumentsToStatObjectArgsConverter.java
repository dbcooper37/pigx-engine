package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToObjectConditionalReadConverter;
import com.pigx.engine.oss.specification.arguments.object.GetObjectMetadataArguments;
import io.minio.StatObjectArgs;


public class ArgumentsToStatObjectArgsConverter extends ArgumentsToObjectConditionalReadConverter<GetObjectMetadataArguments, StatObjectArgs, StatObjectArgs.Builder> {
    @Override
    public StatObjectArgs.Builder getBuilder() {
        return StatObjectArgs.builder();
    }
}
