package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.bucket.CreateBucketArguments;
import io.minio.MakeBucketArgs;
import org.apache.commons.lang3.ObjectUtils;


public class ArgumentsToMakeBucketArgsConverter extends ArgumentsToBucketConverter<CreateBucketArguments, MakeBucketArgs, MakeBucketArgs.Builder> {

    @Override
    public void prepare(CreateBucketArguments arguments, MakeBucketArgs.Builder builder) {
        if (ObjectUtils.isNotEmpty(arguments.getObjectLock())) {
            builder.objectLock(arguments.getObjectLock());
        }
        super.prepare(arguments, builder);
    }

    @Override
    public MakeBucketArgs.Builder getBuilder() {
        return MakeBucketArgs.builder();
    }
}
