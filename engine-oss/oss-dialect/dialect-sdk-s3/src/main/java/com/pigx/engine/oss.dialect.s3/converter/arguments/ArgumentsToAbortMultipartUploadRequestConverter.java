package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.pigx.engine.oss.specification.arguments.multipart.AbortMultipartUploadArguments;
import org.springframework.core.convert.converter.Converter;


public class ArgumentsToAbortMultipartUploadRequestConverter implements Converter<AbortMultipartUploadArguments, AbortMultipartUploadRequest> {
    @Override
    public AbortMultipartUploadRequest convert(AbortMultipartUploadArguments source) {
        return new AbortMultipartUploadRequest(source.getBucketName(), source.getObjectName(), source.getUploadId());
    }
}
