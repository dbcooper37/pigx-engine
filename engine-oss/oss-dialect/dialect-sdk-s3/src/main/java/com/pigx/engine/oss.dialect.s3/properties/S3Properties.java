package com.pigx.engine.oss.dialect.s3.properties;

import com.pigx.engine.oss.dialect.core.constants.OssConstants;
import com.pigx.engine.oss.dialect.core.properties.AbstractOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = OssConstants.PROPERTY_OSS_S3)
public class S3Properties extends AbstractOssProperties {

}
