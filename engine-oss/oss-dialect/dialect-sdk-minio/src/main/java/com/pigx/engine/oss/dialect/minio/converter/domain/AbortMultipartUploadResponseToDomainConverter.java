package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.multipart.AbortMultipartUploadDomain;
import io.minio.AbortMultipartUploadResponse;
import org.springframework.core.convert.converter.Converter;


public class AbortMultipartUploadResponseToDomainConverter implements Converter<AbortMultipartUploadResponse, AbortMultipartUploadDomain> {
    @Override
    public AbortMultipartUploadDomain convert(AbortMultipartUploadResponse source) {

        AbortMultipartUploadDomain domain = new AbortMultipartUploadDomain();
        domain.setUploadId(source.uploadId());
        domain.setBucketName(source.bucket());
        domain.setRegion(source.region());
        domain.setObjectName(source.object());
        return domain;
    }
}
