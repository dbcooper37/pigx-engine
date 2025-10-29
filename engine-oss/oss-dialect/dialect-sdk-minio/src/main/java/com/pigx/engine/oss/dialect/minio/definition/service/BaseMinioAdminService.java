package com.pigx.engine.oss.dialect.minio.definition.service;

import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.service.BaseOssService;
import io.minio.admin.MinioAdminClient;


public abstract class BaseMinioAdminService extends BaseOssService<MinioAdminClient> {

    public BaseMinioAdminService(AbstractObjectPool<MinioAdminClient> ossClientObjectPool) {
        super(ossClientObjectPool);
    }
}
