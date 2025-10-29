package com.pigx.engine.oss.dialect.core.service;


import com.pigx.engine.core.definition.support.AbstractObjectPool;


public abstract class BaseOssService<T> {

    private final AbstractObjectPool<T> objectPool;

    public BaseOssService(AbstractObjectPool<T> objectPool) {
        this.objectPool = objectPool;
    }

    protected T getClient() {
        return objectPool.get();
    }

    protected void close(T client) {
        objectPool.close(client);
    }
}
