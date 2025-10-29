package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToObjectVersionConverter;
import com.pigx.engine.oss.specification.arguments.object.DeleteObjectArguments;
import io.minio.RemoveObjectArgs;
import org.apache.commons.lang3.ObjectUtils;


public class ArgumentsToRemoveObjectArgsConverter extends ArgumentsToObjectVersionConverter<DeleteObjectArguments, RemoveObjectArgs, RemoveObjectArgs.Builder> {

    @Override
    public void prepare(DeleteObjectArguments arguments, RemoveObjectArgs.Builder builder) {
        if (ObjectUtils.isNotEmpty(arguments.getBypassGovernanceMode())) {
            builder.bypassGovernanceMode(arguments.getBypassGovernanceMode());
        }
        super.prepare(arguments, builder);
    }

    @Override
    public RemoveObjectArgs.Builder getBuilder() {
        return RemoveObjectArgs.builder();
    }
}
