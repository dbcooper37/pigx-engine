package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToObjectVersionConverter;
import com.pigx.engine.oss.specification.arguments.object.GeneratePresignedUrlArguments;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;


public class ArgumentsToGetPreSignedObjectUrlConverter extends ArgumentsToObjectVersionConverter<GeneratePresignedUrlArguments, GetPresignedObjectUrlArgs, GetPresignedObjectUrlArgs.Builder> {

    @Override
    public void prepare(GeneratePresignedUrlArguments arguments, GetPresignedObjectUrlArgs.Builder builder) {

        builder.method(Method.valueOf(arguments.getMethod().name()));
        builder.expiry(Math.toIntExact(arguments.getExpiration().toSeconds()));
        builder.versionId(arguments.getVersionId());

        super.prepare(arguments, builder);
    }

    @Override
    public GetPresignedObjectUrlArgs.Builder getBuilder() {
        return GetPresignedObjectUrlArgs.builder();
    }
}
