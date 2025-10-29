package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.oss.specification.arguments.base.BucketArguments;
import io.minio.BucketArgs;
import org.apache.commons.lang3.StringUtils;


public abstract class ArgumentsToBucketConverter<S extends BucketArguments, T extends BucketArgs, B extends BucketArgs.Builder<B, T>> extends ArgumentsToBaseConverter<S, T, B> {

    @Override
    public void prepare(S arguments, B builder) {

        builder.bucket(arguments.getBucketName());

        if (StringUtils.isNotBlank(arguments.getRegion())) {
            builder.region(arguments.getRegion());
        }

        super.prepare(arguments, builder);
    }
}
