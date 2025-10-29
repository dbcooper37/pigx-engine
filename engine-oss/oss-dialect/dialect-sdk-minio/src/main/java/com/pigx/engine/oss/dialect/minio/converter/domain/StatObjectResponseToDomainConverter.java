package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.core.definition.utils.DateTimeUtils;
import com.pigx.engine.oss.specification.domain.object.ObjectMetadataDomain;
import io.minio.StatObjectResponse;
import org.springframework.core.convert.converter.Converter;


public class StatObjectResponseToDomainConverter implements Converter<StatObjectResponse, ObjectMetadataDomain> {
    @Override
    public ObjectMetadataDomain convert(StatObjectResponse source) {

        ObjectMetadataDomain domain = new ObjectMetadataDomain();
        domain.setUserMetadata(source.userMetadata());
        domain.setContentLength(source.size());
        domain.setContentType(source.contentType());
        domain.setLastModified(DateTimeUtils.zonedDateTimeToDate(source.lastModified()));
        domain.setEtag(source.etag());
        domain.setVersionId(source.versionId());
        domain.setBucketName(source.bucket());
        domain.setRegion(source.region());
        domain.setObjectName(source.object());

        return domain;
    }
}
