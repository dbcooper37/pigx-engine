package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.oss.specification.arguments.base.ObjectVersionArguments;
import io.minio.ObjectVersionArgs;
import org.apache.commons.lang3.StringUtils;


public abstract class ArgumentsToObjectVersionConverter<S extends ObjectVersionArguments, T extends ObjectVersionArgs, B extends ObjectVersionArgs.Builder<B, T>> extends ArgumentsToObjectConverter<S, T, B> {
    @Override
    public void prepare(S arguments, B builder) {
        if (StringUtils.isNotBlank(arguments.getVersionId())) {
            builder.versionId(arguments.getVersionId());
        }
        super.prepare(arguments, builder);
    }
}
