package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.bucket.DeleteBucketArguments;
import io.minio.RemoveBucketArgs;


public class ArgumentsToRemoveBucketArgsConverter extends ArgumentsToBucketConverter<DeleteBucketArguments, RemoveBucketArgs, RemoveBucketArgs.Builder> {

    @Override
    public RemoveBucketArgs.Builder getBuilder() {
        return RemoveBucketArgs.builder();
    }
}
