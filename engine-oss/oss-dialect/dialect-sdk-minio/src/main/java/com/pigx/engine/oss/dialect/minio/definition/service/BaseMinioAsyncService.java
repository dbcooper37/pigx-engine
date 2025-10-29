package com.pigx.engine.oss.dialect.minio.definition.service;

import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.service.BaseOssService;
import com.pigx.engine.oss.dialect.minio.definition.pool.MinioAsyncClient;


public abstract class BaseMinioAsyncService extends BaseOssService<MinioAsyncClient> {

    public BaseMinioAsyncService(AbstractObjectPool<MinioAsyncClient> ossClientObjectPool) {
        super(ossClientObjectPool);
    }
}
