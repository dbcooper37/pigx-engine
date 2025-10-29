package com.pigx.engine.oss.dialect.minio.properties;

import com.pigx.engine.oss.dialect.core.constants.OssConstants;
import com.pigx.engine.oss.dialect.core.properties.AbstractOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = OssConstants.PROPERTY_OSS_MINIO)
public class MinioProperties extends AbstractOssProperties {

}
