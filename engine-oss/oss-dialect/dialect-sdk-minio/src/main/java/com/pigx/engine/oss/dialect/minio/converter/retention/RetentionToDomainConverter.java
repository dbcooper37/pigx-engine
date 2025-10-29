package com.pigx.engine.oss.dialect.minio.converter.retention;

import com.pigx.engine.core.definition.utils.DateTimeUtils;
import com.pigx.engine.oss.dialect.minio.domain.RetentionDomain;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import io.minio.messages.Retention;
import io.minio.messages.RetentionMode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class RetentionToDomainConverter implements Converter<Retention, RetentionDomain> {

    private final Converter<RetentionMode, RetentionModeEnums> toEnums;

    public RetentionToDomainConverter() {
        this.toEnums = new RetentionModeToEnumConverter();
    }

    @Override
    public RetentionDomain convert(Retention retention) {

        RetentionDomain retentionDomain = new RetentionDomain();
        if (ObjectUtils.isNotEmpty(retention)) {
            retentionDomain.setMode(toEnums.convert(retention.mode()));
            if (ObjectUtils.isNotEmpty(retention.retainUntilDate())) {
                retentionDomain.setRetainUntilDate(DateTimeUtils.zonedDateTimeToString(retention.retainUntilDate()));
            }
            return retentionDomain;
        }

        return null;
    }
}
