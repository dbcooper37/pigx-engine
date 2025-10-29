package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.DeleteObjectsArguments;
import com.pigx.engine.oss.specification.arguments.object.DeletedObjectArguments;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;


public class ArgumentsToDeleteObjectsRequestConverter extends ArgumentsToBucketConverter<DeleteObjectsArguments, DeleteObjectsRequest> {

    @Override
    public void prepare(DeleteObjectsArguments arguments, DeleteObjectsRequest request) {
        List<DeletedObjectArguments> keys = arguments.getObjects();
        if (CollectionUtils.isNotEmpty(keys)) {
            List<DeleteObjectsRequest.KeyVersion> values = keys.stream().map(item -> new DeleteObjectsRequest.KeyVersion(item.getObjectName(), item.getVersionId())).toList();
            request.setKeys(values);
        }

        request.setQuiet(arguments.getQuiet());
        super.prepare(arguments, request);
    }

    @Override
    public DeleteObjectsRequest getInstance(DeleteObjectsArguments arguments) {
        return new DeleteObjectsRequest(arguments.getBucketName());
    }
}
