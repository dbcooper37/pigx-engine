package com.pigx.engine.oss.dialect.minio.converter.retention;

import com.pigx.engine.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import com.pigx.engine.oss.dialect.minio.enums.RetentionUnitEnums;
import io.minio.messages.ObjectLockConfiguration;
import io.minio.messages.RetentionDuration;
import io.minio.messages.RetentionMode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class ObjectLockConfigurationToDomainConverter implements Converter<ObjectLockConfiguration, ObjectLockConfigurationDomain> {

    @Override
    public ObjectLockConfigurationDomain convert(ObjectLockConfiguration objectLockConfiguration) {

        if (ObjectUtils.isNotEmpty(objectLockConfiguration)) {
            RetentionMode mode = objectLockConfiguration.mode();
            RetentionDuration duration = objectLockConfiguration.duration();

            if (ObjectUtils.isNotEmpty(mode) && ObjectUtils.isNotEmpty(duration)) {
                ObjectLockConfigurationDomain configurationDo = new ObjectLockConfigurationDomain();
                configurationDo.setMode(RetentionModeEnums.valueOf(mode.name()));
                configurationDo.setUnit(RetentionUnitEnums.valueOf(duration.unit().name()));
                configurationDo.setValidity(duration.duration());
                return configurationDo;
            }
        }

        return null;
    }
}
