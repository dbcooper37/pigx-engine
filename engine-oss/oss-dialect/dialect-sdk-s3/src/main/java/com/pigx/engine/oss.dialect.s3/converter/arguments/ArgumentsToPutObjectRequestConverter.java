package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.PutObjectArguments;


public class ArgumentsToPutObjectRequestConverter extends ArgumentsToBucketConverter<PutObjectArguments, PutObjectRequest> {
    @Override
    public PutObjectRequest getInstance(PutObjectArguments source) {
        return new PutObjectRequest(source.getBucketName(), source.getObjectName(), source.getInputStream(), new ObjectMetadata());
    }
}
