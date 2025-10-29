package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.pigx.engine.oss.specification.domain.multipart.InitiateMultipartUploadDomain;
import org.springframework.core.convert.converter.Converter;


public class InitiateMultipartUploadResultToDomainConverter implements Converter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> {
    @Override
    public InitiateMultipartUploadDomain convert(InitiateMultipartUploadResult source) {

        InitiateMultipartUploadDomain domain = new InitiateMultipartUploadDomain();
        domain.setUploadId(source.getUploadId());
        domain.setBucketName(source.getBucketName());
        domain.setObjectName(source.getKey());
        return domain;
    }
}
