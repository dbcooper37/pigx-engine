package com.pigx.engine.oss.dialect.s3.definition.pool;

import com.amazonaws.services.s3.AmazonS3;
import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;


public class S3ClientObjectPool extends AbstractObjectPool<AmazonS3> {

    public S3ClientObjectPool(AbstractOssClientPooledObjectFactory<AmazonS3> factory) {
        super(factory, factory.getOssProperties().getPool());
    }
}
