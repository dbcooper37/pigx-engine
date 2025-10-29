package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.ListObjectsV2Arguments;
import io.minio.ListObjectsArgs;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


public class ArgumentsToListObjectsV2ArgsConverter extends ArgumentsToBucketConverter<ListObjectsV2Arguments, ListObjectsArgs, ListObjectsArgs.Builder> {

    @Override
    public void prepare(ListObjectsV2Arguments arguments, ListObjectsArgs.Builder builder) {
        builder.delimiter(arguments.getDelimiter());
        builder.useUrlEncodingType(StringUtils.isNotBlank(arguments.getEncodingType()));
        builder.maxKeys(arguments.getMaxKeys());
        builder.prefix(arguments.getPrefix());
        builder.recursive(false);
        builder.useApiVersion1(false);
        builder.includeUserMetadata(true);
        builder.includeVersions(false);

        if (StringUtils.isNotBlank(arguments.getMarker())) {
            builder.keyMarker(arguments.getMarker());
        }

        if (StringUtils.isNotBlank(arguments.getContinuationToken())) {
            builder.continuationToken(arguments.getContinuationToken());
        }

        if (ObjectUtils.isNotEmpty(arguments.getFetchOwner())) {
            builder.fetchOwner(arguments.getFetchOwner());
        }

        super.prepare(arguments, builder);
    }

    @Override
    public ListObjectsArgs.Builder getBuilder() {
        return ListObjectsArgs.builder();
    }
}
