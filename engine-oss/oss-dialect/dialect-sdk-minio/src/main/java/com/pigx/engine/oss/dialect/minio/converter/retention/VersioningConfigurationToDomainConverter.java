package com.pigx.engine.oss.dialect.minio.converter.retention;

import com.pigx.engine.oss.dialect.minio.domain.VersioningConfigurationDomain;
import io.minio.messages.VersioningConfiguration;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class VersioningConfigurationToDomainConverter implements Converter<VersioningConfiguration, VersioningConfigurationDomain> {
    @Override
    public VersioningConfigurationDomain convert(VersioningConfiguration versioningConfiguration) {

        if (ObjectUtils.isNotEmpty(versioningConfiguration)) {
            VersioningConfigurationDomain domain = new VersioningConfigurationDomain();
            domain.setStatus(versioningConfiguration.status().name());
            domain.setMfaDelete(versioningConfiguration.isMfaDeleteEnabled());
            return domain;
        }

        return null;
    }
}
