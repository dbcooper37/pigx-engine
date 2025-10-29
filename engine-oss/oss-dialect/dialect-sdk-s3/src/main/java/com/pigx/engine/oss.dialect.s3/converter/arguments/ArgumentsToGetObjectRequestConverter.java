package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.GetObjectArguments;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;


public class ArgumentsToGetObjectRequestConverter extends ArgumentsToBucketConverter<GetObjectArguments, GetObjectRequest> {

    @Override
    public void prepare(GetObjectArguments arguments, GetObjectRequest request) {
        if (ObjectUtils.isNotEmpty(arguments.getLength()) && ObjectUtils.isNotEmpty(arguments.getOffset())) {
            long start = arguments.getOffset();
            long end = arguments.getOffset() + arguments.getLength() - 1L;
            request.setRange(start, end);
        }

        if (CollectionUtils.isNotEmpty(arguments.getMatchETag())) {
            request.setMatchingETagConstraints(arguments.getMatchETag());
        }

        if (CollectionUtils.isNotEmpty(arguments.getNotMatchEtag())) {
            request.setNonmatchingETagConstraints(arguments.getNotMatchEtag());
        }

        if (ObjectUtils.isNotEmpty(arguments.getModifiedSince())) {
            request.setModifiedSinceConstraint(arguments.getModifiedSince());
        }

        if (ObjectUtils.isNotEmpty(arguments.getUnmodifiedSince())) {
            request.setUnmodifiedSinceConstraint(arguments.getUnmodifiedSince());
        }
        super.prepare(arguments, request);
    }

    @Override
    public GetObjectRequest getInstance(GetObjectArguments source) {
        return new GetObjectRequest(source.getBucketName(), source.getObjectName(), source.getVersionId());
    }
}
