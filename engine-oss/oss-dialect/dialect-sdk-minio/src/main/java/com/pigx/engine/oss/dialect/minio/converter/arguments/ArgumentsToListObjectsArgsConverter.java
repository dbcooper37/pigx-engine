package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.ListObjectsArguments;
import io.minio.ListObjectsArgs;
import org.apache.commons.lang3.StringUtils;


public class ArgumentsToListObjectsArgsConverter extends ArgumentsToBucketConverter<ListObjectsArguments, ListObjectsArgs, ListObjectsArgs.Builder> {

    @Override
    public void prepare(ListObjectsArguments arguments, ListObjectsArgs.Builder builder) {
        builder.delimiter(arguments.getDelimiter());
        builder.useUrlEncodingType(StringUtils.isNotBlank(arguments.getEncodingType()));
        builder.maxKeys(arguments.getMaxKeys());
        builder.prefix(arguments.getPrefix());
        builder.recursive(false);
        builder.useApiVersion1(true);

        if (StringUtils.isNotBlank(arguments.getMarker())) {
            builder.keyMarker(arguments.getMarker());
        }

        super.prepare(arguments, builder);
    }

    @Override
    public ListObjectsArgs.Builder getBuilder() {
        return ListObjectsArgs.builder();
    }
}
