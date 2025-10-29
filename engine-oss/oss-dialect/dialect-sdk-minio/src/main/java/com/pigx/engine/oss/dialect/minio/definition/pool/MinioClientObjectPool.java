package com.pigx.engine.oss.dialect.minio.definition.pool;


import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import io.minio.MinioClient;


public class MinioClientObjectPool extends AbstractObjectPool<MinioClient> {

    public MinioClientObjectPool(AbstractOssClientPooledObjectFactory<MinioClient> factory) {
        super(factory, factory.getOssProperties().getPool());
    }
}
