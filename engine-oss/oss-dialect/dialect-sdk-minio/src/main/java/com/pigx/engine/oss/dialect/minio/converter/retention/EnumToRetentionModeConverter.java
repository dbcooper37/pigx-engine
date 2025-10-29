package com.pigx.engine.oss.dialect.minio.converter.retention;

import com.google.common.base.Enums;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import io.minio.messages.RetentionMode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class EnumToRetentionModeConverter implements Converter<RetentionModeEnums, RetentionMode> {
    @Override
    public RetentionMode convert(RetentionModeEnums enums) {
        if (ObjectUtils.isNotEmpty(enums)) {
            return Enums.getIfPresent(RetentionMode.class, enums.name()).orNull();
        }

        return null;
    }
}
