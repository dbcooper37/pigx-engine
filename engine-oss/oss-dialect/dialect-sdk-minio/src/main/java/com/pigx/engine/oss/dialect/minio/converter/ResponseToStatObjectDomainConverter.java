package com.pigx.engine.oss.dialect.minio.converter;

import com.pigx.engine.core.definition.utils.DateTimeUtils;
import com.pigx.engine.oss.dialect.minio.converter.retention.RetentionModeToEnumConverter;
import com.pigx.engine.oss.dialect.minio.domain.StatObjectDomain;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import io.minio.StatObjectResponse;
import io.minio.messages.RetentionMode;
import org.springframework.core.convert.converter.Converter;


public class ResponseToStatObjectDomainConverter implements Converter<StatObjectResponse, StatObjectDomain> {

    private final Converter<RetentionMode, RetentionModeEnums> toRetentionModeEnums;

    public ResponseToStatObjectDomainConverter() {
        this.toRetentionModeEnums = new RetentionModeToEnumConverter();
    }

    @Override
    public StatObjectDomain convert(StatObjectResponse response) {

        StatObjectDomain domain = new StatObjectDomain();
        domain.setEtag(response.etag());
        domain.setSize(response.size());
        domain.setLastModified(DateTimeUtils.zonedDateTimeToString(response.lastModified()));
        domain.setRetentionMode(toRetentionModeEnums.convert(response.retentionMode()));
        domain.setRetentionRetainUntilDate(DateTimeUtils.zonedDateTimeToString(response.retentionRetainUntilDate()));
        domain.setLegalHold(response.legalHold().status());
        domain.setDeleteMarker(response.deleteMarker());
        domain.setUserMetadata(response.userMetadata());
        domain.setBucketName(response.bucket());
        domain.setRegion(response.region());
        domain.setObjectName(response.object());

        return domain;
    }
}
