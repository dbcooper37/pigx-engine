package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.bucket.DeleteBucketArguments;


public class ArgumentsToDeleteBucketRequestConverter extends ArgumentsToBucketConverter<DeleteBucketArguments, DeleteBucketRequest> {
    @Override
    public DeleteBucketRequest getInstance(DeleteBucketArguments arguments) {
        return new DeleteBucketRequest(arguments.getBucketName());
    }
}
