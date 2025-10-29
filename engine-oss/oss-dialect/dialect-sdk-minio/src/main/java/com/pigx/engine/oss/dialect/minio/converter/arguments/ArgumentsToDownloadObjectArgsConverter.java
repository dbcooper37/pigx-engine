package com.pigx.engine.oss.dialect.minio.converter.arguments;

import com.pigx.engine.oss.dialect.minio.definition.arguments.ArgumentsToObjectReadConverter;
import com.pigx.engine.oss.specification.arguments.object.DownloadObjectArguments;
import io.minio.DownloadObjectArgs;


public class ArgumentsToDownloadObjectArgsConverter extends ArgumentsToObjectReadConverter<DownloadObjectArguments, DownloadObjectArgs, DownloadObjectArgs.Builder> {

    @Override
    public void prepare(DownloadObjectArguments arguments, DownloadObjectArgs.Builder builder) {
        builder.filename(arguments.getFilename());
        builder.overwrite(arguments.getOverwrite());

        super.prepare(arguments, builder);
    }

    @Override
    public DownloadObjectArgs.Builder getBuilder() {
        return DownloadObjectArgs.builder();
    }
}
