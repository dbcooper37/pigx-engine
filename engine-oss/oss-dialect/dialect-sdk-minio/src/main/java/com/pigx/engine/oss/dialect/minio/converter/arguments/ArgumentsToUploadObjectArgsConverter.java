package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToPutObjectBaseConverter;
import com.pigx.engine.oss.specification.arguments.object.UploadObjectArguments;
import io.minio.UploadObjectArgs;

import java.io.IOException;


public class ArgumentsToUploadObjectArgsConverter extends ArgumentsToPutObjectBaseConverter<UploadObjectArguments, UploadObjectArgs, UploadObjectArgs.Builder> {

    @Override
    public void prepare(UploadObjectArguments arguments, UploadObjectArgs.Builder builder) {

        try {
            builder.filename(arguments.getFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        builder.contentType(arguments.getContentType());

        super.prepare(arguments, builder);
    }

    @Override
    public UploadObjectArgs.Builder getBuilder() {
        return UploadObjectArgs.builder();
    }
}
