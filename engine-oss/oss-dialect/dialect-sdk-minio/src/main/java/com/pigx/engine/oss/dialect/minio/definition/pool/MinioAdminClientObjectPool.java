package com.pigx.engine.oss.dialect.minio.definition.pool;

import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import io.minio.admin.MinioAdminClient;


public class MinioAdminClientObjectPool extends AbstractObjectPool<MinioAdminClient> {

    public MinioAdminClientObjectPool(AbstractOssClientPooledObjectFactory<MinioAdminClient> factory) {
        super(factory, factory.getOssProperties().getPool());
    }
}
