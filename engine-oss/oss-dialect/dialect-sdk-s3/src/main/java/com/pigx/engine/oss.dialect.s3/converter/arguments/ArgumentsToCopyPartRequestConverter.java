package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.CopyPartRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.multipart.UploadPartCopyArguments;


public class ArgumentsToCopyPartRequestConverter extends ArgumentsToBucketConverter<UploadPartCopyArguments, CopyPartRequest> {
    @Override
    public CopyPartRequest getInstance(UploadPartCopyArguments arguments) {

        CopyPartRequest request = new CopyPartRequest();

        return request
                .withSourceBucketName(arguments.getBucketName())
                .withSourceKey(arguments.getObjectName())
                .withUploadId(arguments.getUploadId())
                .withPartNumber(arguments.getPartNumber())
                .withDestinationBucketName(arguments.getDestinationBucketName())
                .withDestinationKey(arguments.getDestinationObjectName())
                .withMatchingETagConstraints(arguments.getMatchingETagConstraints())
                .withNonmatchingETagConstraints(arguments.getNonmatchingEtagConstraints())
                .withModifiedSinceConstraint(arguments.getModifiedSinceConstraint())
                .withUnmodifiedSinceConstraint(arguments.getUnmodifiedSinceConstraint());
    }
}
