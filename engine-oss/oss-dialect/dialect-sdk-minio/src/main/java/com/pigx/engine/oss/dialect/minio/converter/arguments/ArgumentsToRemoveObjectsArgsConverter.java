package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.DeleteObjectsArguments;
import io.minio.RemoveObjectsArgs;
import io.minio.messages.DeleteObject;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;


public class ArgumentsToRemoveObjectsArgsConverter extends ArgumentsToBucketConverter<DeleteObjectsArguments, RemoveObjectsArgs, RemoveObjectsArgs.Builder> {

    @Override
    public void prepare(DeleteObjectsArguments arguments, RemoveObjectsArgs.Builder builder) {
        if (ObjectUtils.isNotEmpty(arguments.getBypassGovernanceMode())) {
            builder.bypassGovernanceMode(arguments.getBypassGovernanceMode());
        }

        List<DeleteObject> deleteObjects = arguments.getObjects().stream().map(item -> new DeleteObject(item.getObjectName(), item.getVersionId())).toList();
        builder.objects(deleteObjects);

        super.prepare(arguments, builder);
    }

    @Override
    public RemoveObjectsArgs.Builder getBuilder() {
        return RemoveObjectsArgs.builder();
    }
}
