package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.multipart.InitiateMultipartUploadDomain;
import io.minio.messages.InitiateMultipartUploadResult;
import org.springframework.core.convert.converter.Converter;


public class InitiateMultipartUploadResultToDomainConverter implements Converter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> {
    @Override
    public InitiateMultipartUploadDomain convert(InitiateMultipartUploadResult source) {

        InitiateMultipartUploadDomain domain = new InitiateMultipartUploadDomain();
        domain.setUploadId(source.uploadId());
        domain.setBucketName(source.bucketName());
        domain.setObjectName(source.objectName());
        return domain;
    }
}
