package com.pigx.engine.oss.dialect.core.client;

import com.pigx.engine.oss.dialect.core.properties.AbstractOssProperties;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;


public abstract class AbstractOssClientPooledObjectFactory<T> extends BasePooledObjectFactory<T> {

    private final AbstractOssProperties ossProperties;

    public AbstractOssClientPooledObjectFactory(AbstractOssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    public AbstractOssProperties getOssProperties() {
        return ossProperties;
    }

    @Override
    public PooledObject<T> wrap(T obj) {
        return new DefaultPooledObject<>(obj);
    }
}
