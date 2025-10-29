package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.DeleteObjectArguments;


public class ArgumentsToDeleteObjectRequestConverter extends ArgumentsToBucketConverter<DeleteObjectArguments, DeleteObjectRequest> {

    @Override
    public DeleteObjectRequest getInstance(DeleteObjectArguments arguments) {
        return new DeleteObjectRequest(arguments.getBucketName(), arguments.getObjectName());
    }
}
