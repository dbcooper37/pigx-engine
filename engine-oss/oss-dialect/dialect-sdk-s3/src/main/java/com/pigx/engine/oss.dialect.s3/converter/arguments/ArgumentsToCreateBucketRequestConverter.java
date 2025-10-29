package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.bucket.CreateBucketArguments;


public class ArgumentsToCreateBucketRequestConverter extends ArgumentsToBucketConverter<CreateBucketArguments, CreateBucketRequest> {

    @Override
    public CreateBucketRequest getInstance(CreateBucketArguments arguments) {
        return new CreateBucketRequest(arguments.getBucketName());
    }
}
