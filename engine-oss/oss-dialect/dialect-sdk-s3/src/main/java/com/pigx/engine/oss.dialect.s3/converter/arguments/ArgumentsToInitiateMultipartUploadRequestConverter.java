package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.multipart.InitiateMultipartUploadArguments;


public class ArgumentsToInitiateMultipartUploadRequestConverter extends ArgumentsToBucketConverter<InitiateMultipartUploadArguments, InitiateMultipartUploadRequest> {

    @Override
    public InitiateMultipartUploadRequest getInstance(InitiateMultipartUploadArguments arguments) {
        return new InitiateMultipartUploadRequest(arguments.getBucketName(), arguments.getObjectName());
    }
}
