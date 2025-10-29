package com.pigx.engine.oss.dialect.minio.definition.domain;

import com.pigx.engine.oss.specification.domain.base.ObjectWriteDomain;
import io.minio.ObjectWriteResponse;


public abstract class ObjectWriteResponseToDomain<T extends ObjectWriteDomain> extends GenericResponseToDomainConverter<ObjectWriteResponse, T> {

    @Override
    public void prepare(ObjectWriteResponse response, T domain) {
        domain.setEtag(response.etag());
        domain.setVersionId(response.versionId());
        super.prepare(response, domain);
    }
}
