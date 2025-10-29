package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.dialect.minio.definition.domain.ObjectWriteResponseToDomain;
import com.pigx.engine.oss.specification.domain.multipart.CompleteMultipartUploadDomain;
import io.minio.ObjectWriteResponse;


public class ObjectWriteResponseToCompleteMultipartUploadDomainConverter extends ObjectWriteResponseToDomain<CompleteMultipartUploadDomain> {
    @Override
    public CompleteMultipartUploadDomain getInstance(ObjectWriteResponse source) {
        return new CompleteMultipartUploadDomain();
    }
}
