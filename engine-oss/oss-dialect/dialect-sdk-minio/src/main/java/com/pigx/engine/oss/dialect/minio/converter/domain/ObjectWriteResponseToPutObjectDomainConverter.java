package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.dialect.minio.definition.domain.ObjectWriteResponseToDomain;
import com.pigx.engine.oss.specification.domain.object.PutObjectDomain;
import io.minio.ObjectWriteResponse;


public class ObjectWriteResponseToPutObjectDomainConverter extends ObjectWriteResponseToDomain<PutObjectDomain> {
    @Override
    public PutObjectDomain getInstance(ObjectWriteResponse source) {
        return new PutObjectDomain();
    }
}
