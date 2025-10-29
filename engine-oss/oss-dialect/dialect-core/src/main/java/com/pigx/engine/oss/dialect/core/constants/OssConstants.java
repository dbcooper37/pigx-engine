package com.pigx.engine.oss.dialect.core.constants;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface OssConstants extends BaseConstants {

    String PROPERTY_OSS_MINIO = PROPERTY_PREFIX_OSS + ".minio";
    String PROPERTY_OSS_S3 = PROPERTY_PREFIX_OSS + ".s3";
    String PROPERTY_OSS_ALIYUN = PROPERTY_PREFIX_OSS + ".aliyun";

    String ITEM_OSS_DIALECT = PROPERTY_PREFIX_OSS + ".dialect";
}
