package com.pigx.engine.oss.dialect.minio.definition.pool;

import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;


public class MinioAsyncClientObjectPool extends AbstractObjectPool<MinioAsyncClient> {

    public MinioAsyncClientObjectPool(AbstractOssClientPooledObjectFactory<MinioAsyncClient> factory) {
        super(factory, factory.getOssProperties().getPool());
    }
}
