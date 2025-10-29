package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.pigx.engine.oss.specification.domain.multipart.CompleteMultipartUploadDomain;
import org.springframework.core.convert.converter.Converter;


public class CompleteMultipartUploadResultToDomainConverter implements Converter<CompleteMultipartUploadResult, CompleteMultipartUploadDomain> {
    @Override
    public CompleteMultipartUploadDomain convert(CompleteMultipartUploadResult source) {

        CompleteMultipartUploadDomain domain = new CompleteMultipartUploadDomain();
        domain.setEtag(source.getETag());
        domain.setVersionId(source.getVersionId());
        domain.setBucketName(source.getBucketName());
        domain.setObjectName(source.getKey());

        return domain;
    }
}
