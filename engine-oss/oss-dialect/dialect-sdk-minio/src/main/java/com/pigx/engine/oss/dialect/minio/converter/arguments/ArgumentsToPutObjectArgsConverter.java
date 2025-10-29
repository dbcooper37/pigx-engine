package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToPutObjectBaseConverter;
import com.pigx.engine.oss.specification.arguments.object.PutObjectArguments;
import io.minio.PutObjectArgs;
import org.apache.commons.lang3.StringUtils;


public class ArgumentsToPutObjectArgsConverter extends ArgumentsToPutObjectBaseConverter<PutObjectArguments, PutObjectArgs, PutObjectArgs.Builder> {

    @Override
    public void prepare(PutObjectArguments arguments, PutObjectArgs.Builder builder) {
        builder.stream(arguments.getInputStream(), arguments.getObjectSize(), arguments.getPartSize());

        if (StringUtils.isNotBlank(arguments.getContentType())) {
            builder.contentType(arguments.getContentType());
        }

        super.prepare(arguments, builder);
    }

    @Override
    public PutObjectArgs.Builder getBuilder() {
        return PutObjectArgs.builder();
    }
}
