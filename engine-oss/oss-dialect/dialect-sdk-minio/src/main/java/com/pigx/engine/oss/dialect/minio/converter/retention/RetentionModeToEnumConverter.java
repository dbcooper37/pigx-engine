package com.pigx.engine.oss.dialect.minio.converter.retention;

import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import io.minio.messages.RetentionMode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class RetentionModeToEnumConverter implements Converter<RetentionMode, RetentionModeEnums> {

    @Override
    public RetentionModeEnums convert(RetentionMode retentionMode) {

        if (ObjectUtils.isNotEmpty(retentionMode)) {
            String retentionModeName = retentionMode.name();
            return RetentionModeEnums.valueOf(retentionModeName);
        }

        return null;
    }
}
