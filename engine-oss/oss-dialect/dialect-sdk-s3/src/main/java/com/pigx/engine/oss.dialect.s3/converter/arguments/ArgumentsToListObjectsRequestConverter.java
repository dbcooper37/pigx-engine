package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.ListObjectsArguments;


public class ArgumentsToListObjectsRequestConverter extends ArgumentsToBucketConverter<ListObjectsArguments, ListObjectsRequest> {

    @Override
    public ListObjectsRequest getInstance(ListObjectsArguments arguments) {
        return new ListObjectsRequest(arguments.getBucketName(), arguments.getPrefix(), arguments.getMarker(), arguments.getDelimiter(), arguments.getMaxKeys());
    }
}
