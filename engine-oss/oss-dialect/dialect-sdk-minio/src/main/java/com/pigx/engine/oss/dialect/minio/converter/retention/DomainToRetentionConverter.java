package com.pigx.engine.oss.dialect.minio.converter.retention;

import com.pigx.engine.core.definition.utils.DateTimeUtils;
import com.pigx.engine.oss.dialect.minio.domain.RetentionDomain;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import io.minio.messages.Retention;
import io.minio.messages.RetentionMode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;


public class DomainToRetentionConverter implements Converter<RetentionDomain, Retention> {

    private final Converter<RetentionModeEnums, RetentionMode> toRetentionMode = new EnumToRetentionModeConverter();

    @Override
    public Retention convert(RetentionDomain retentionDomain) {
        RetentionMode mode = toRetentionMode.convert(retentionDomain.getMode());
        ZonedDateTime retainUntilDate = DateTimeUtils.stringToZonedDateTime(retentionDomain.getRetainUntilDate());
        if (ObjectUtils.isNotEmpty(mode) && ObjectUtils.isNotEmpty(retainUntilDate)) {
            return new Retention(mode, retainUntilDate);
        } else {
            return null;
        }
    }
}
