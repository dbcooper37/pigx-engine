package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.dialect.minio.definition.domain.ObjectWriteResponseToDomain;
import com.pigx.engine.oss.specification.domain.object.UploadObjectDomain;
import io.minio.ObjectWriteResponse;


public class ObjectWriteResponseToUploadObjectDomainConverter extends ObjectWriteResponseToDomain<UploadObjectDomain> {
    @Override
    public UploadObjectDomain getInstance(ObjectWriteResponse source) {
        return new UploadObjectDomain();
    }
}
