package com.pigx.engine.oss.dialect.minio.definition.domain;

import com.pigx.engine.oss.specification.core.converter.OssConverter;
import com.pigx.engine.oss.specification.domain.base.BaseDomain;
import io.minio.GenericResponse;


public abstract class GenericResponseToDomainConverter<S extends GenericResponse, T extends BaseDomain> implements OssConverter<S, T> {

    @Override
    public void prepare(S source, T instance) {
        instance.setBucketName(source.bucket());
        instance.setRegion(source.region());
        instance.setObjectName(source.object());
    }
}
