package com.pigx.engine.oss.dialect.minio.converter;

import com.pigx.engine.oss.specification.domain.base.ObjectWriteDomain;
import io.minio.ObjectWriteResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class ResponseToObjectWriteDomainConverter implements Converter<ObjectWriteResponse, ObjectWriteDomain> {
    @Override
    public ObjectWriteDomain convert(ObjectWriteResponse response) {
        if (ObjectUtils.isNotEmpty(response)) {
            ObjectWriteDomain domain = new ObjectWriteDomain();
            domain.setEtag(response.etag());
            domain.setVersionId(response.versionId());
            domain.setBucketName(response.bucket());
            domain.setRegion(response.region());
            domain.setObjectName(response.object());
            return domain;
        }

        return null;
    }
}
