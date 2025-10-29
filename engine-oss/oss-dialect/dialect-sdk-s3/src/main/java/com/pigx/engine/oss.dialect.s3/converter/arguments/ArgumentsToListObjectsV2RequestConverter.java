package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.ListObjectsV2Arguments;


public class ArgumentsToListObjectsV2RequestConverter extends ArgumentsToBucketConverter<ListObjectsV2Arguments, ListObjectsV2Request> {

    @Override
    public ListObjectsV2Request getInstance(ListObjectsV2Arguments arguments) {
        ListObjectsV2Request request = new ListObjectsV2Request();
        return request
                .withBucketName(arguments.getBucketName())
                .withDelimiter(arguments.getDelimiter())
                .withEncodingType(arguments.getEncodingType())
                .withMaxKeys(arguments.getMaxKeys())
                .withPrefix(arguments.getPrefix())
                .withContinuationToken(arguments.getContinuationToken())
                .withFetchOwner(arguments.getFetchOwner())
                .withStartAfter(arguments.getMarker());
    }
}
