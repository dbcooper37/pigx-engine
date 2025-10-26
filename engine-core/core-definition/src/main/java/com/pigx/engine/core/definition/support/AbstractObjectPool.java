package com.pigx.engine.core.definition.support;

import com.pigx.engine.core.definition.domain.Pool;
import com.pigx.engine.core.definition.exception.BorrowObjectFromPoolErrorException;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/support/AbstractObjectPool.class */
public abstract class AbstractObjectPool<T> {
    private static final Logger log = LoggerFactory.getLogger(AbstractObjectPool.class);
    private final GenericObjectPool<T> genericObjectPool;

    protected AbstractObjectPool(@Nonnull PooledObjectFactory<T> pooledObjectFactory, @Nonnull Pool pool) {
        GenericObjectPoolConfig<T> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(pool.getMaxTotal().intValue());
        config.setMaxIdle(pool.getMaxIdle().intValue());
        config.setMinIdle(pool.getMinIdle().intValue());
        config.setMaxWait(pool.getMaxWait());
        config.setMinEvictableIdleDuration(pool.getMinEvictableIdleDuration());
        config.setSoftMinEvictableIdleDuration(pool.getSoftMinEvictableIdleDuration());
        config.setLifo(pool.getLifo().booleanValue());
        config.setBlockWhenExhausted(pool.getBlockWhenExhausted().booleanValue());
        this.genericObjectPool = new GenericObjectPool<>(pooledObjectFactory, config);
    }

    public T get() {
        try {
            T t = (T) this.genericObjectPool.borrowObject();
            log.debug("[Herodotus] |- Fetch object from object pool.");
            return t;
        } catch (Exception e) {
            log.error("[Herodotus] |- Can not fetch object from pool.", e);
            throw new BorrowObjectFromPoolErrorException("Can not fetch object from pool.");
        }
    }

    public void close(T client) {
        if (ObjectUtils.isNotEmpty(client)) {
            log.debug("[Herodotus] |- Close object in pool.");
            this.genericObjectPool.returnObject(client);
        }
    }
}
