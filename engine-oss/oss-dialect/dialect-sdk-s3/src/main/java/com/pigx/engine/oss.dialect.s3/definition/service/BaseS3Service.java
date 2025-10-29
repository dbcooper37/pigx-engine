package com.pigx.engine.oss.dialect.s3.definition.service;

import com.amazonaws.services.s3.AmazonS3;
import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.service.BaseOssService;


public abstract class BaseS3Service extends BaseOssService<AmazonS3> {

    public BaseS3Service(AbstractObjectPool<AmazonS3> ossClientObjectPool) {
        super(ossClientObjectPool);
    }
}
