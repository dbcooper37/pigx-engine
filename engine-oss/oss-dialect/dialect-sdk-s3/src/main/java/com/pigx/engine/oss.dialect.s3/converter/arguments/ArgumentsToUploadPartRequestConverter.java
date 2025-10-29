package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.UploadPartRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.multipart.UploadPartArguments;


public class ArgumentsToUploadPartRequestConverter extends ArgumentsToBucketConverter<UploadPartArguments, UploadPartRequest> {
    @Override
    public UploadPartRequest getInstance(UploadPartArguments arguments) {

        UploadPartRequest request = new UploadPartRequest();

        return request
                .withBucketName(arguments.getBucketName())
                .withKey(arguments.getObjectName())
                .withUploadId(arguments.getUploadId())
                .withPartNumber(arguments.getPartNumber())
                .withPartSize(arguments.getPartSize())
                .withInputStream(arguments.getInputStream())
                .withMD5Digest(arguments.getMd5Digest());
    }
}
