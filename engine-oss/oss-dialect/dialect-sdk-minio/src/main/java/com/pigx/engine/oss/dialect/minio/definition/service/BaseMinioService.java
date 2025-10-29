package com.pigx.engine.oss.dialect.minio.definition.service;

import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.service.BaseOssService;
import io.minio.MinioClient;


public abstract class BaseMinioService extends BaseOssService<MinioClient> {

    public BaseMinioService(AbstractObjectPool<MinioClient> ossClientObjectPool) {
        super(ossClientObjectPool);
    }
}
