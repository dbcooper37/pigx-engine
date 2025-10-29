package com.pigx.engine.oss.dialect.minio.converter.retention;

import com.pigx.engine.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import com.pigx.engine.oss.dialect.minio.enums.RetentionUnitEnums;
import io.minio.messages.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class DomainToObjectLockConfigurationConverter implements Converter<ObjectLockConfigurationDomain, ObjectLockConfiguration> {

    private final Converter<RetentionModeEnums, RetentionMode> toRetentionMode = new EnumToRetentionModeConverter();

    @Override
    public ObjectLockConfiguration convert(ObjectLockConfigurationDomain source) {

        if (isRetentionModeValid(source) && isRetentionDurationModeValid(source)) {
            RetentionMode mode = toRetentionMode.convert(source.getMode());
            RetentionDuration duration = getRetentionDuration(source.getUnit(), source.getValidity());
            return new ObjectLockConfiguration(mode, duration);
        }

        return null;
    }

    private boolean isRetentionModeValid(ObjectLockConfigurationDomain source) {
        RetentionModeEnums enums = source.getMode();
        return ObjectUtils.isNotEmpty(enums);
    }

    private boolean isRetentionDurationModeValid(ObjectLockConfigurationDomain source) {
        RetentionUnitEnums enums = source.getUnit();
        Integer duration = source.getValidity();
        return ObjectUtils.isNotEmpty(enums) && ObjectUtils.isNotEmpty(duration) && duration != 0;
    }

    private RetentionDuration getRetentionDuration(RetentionUnitEnums enums, Integer duration) {
        if (enums == RetentionUnitEnums.DAYS) {
            return new RetentionDurationDays(duration);
        } else {
            return new RetentionDurationYears(duration);
        }
    }
}
