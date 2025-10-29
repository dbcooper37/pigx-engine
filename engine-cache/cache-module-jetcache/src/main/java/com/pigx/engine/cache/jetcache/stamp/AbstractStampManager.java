package com.pigx.engine.cache.jetcache.stamp;

import com.alicp.jetcache.AutoReleaseLock;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.pigx.engine.cache.core.exception.StampHasExpiredException;
import com.pigx.engine.cache.core.exception.StampMismatchException;
import com.pigx.engine.cache.core.exception.StampParameterIllegalException;
import com.pigx.engine.cache.jetcache.utils.JetCacheUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public abstract class AbstractStampManager<K, V> implements StampManager<K, V> {

    private static final Logger log = LoggerFactory.getLogger(AbstractStampManager.class);

    private static final Duration DEFAULT_EXPIRE = Duration.ofMinutes(5);

    private Duration expire;
    private final Cache<K, V> cache;

    protected AbstractStampManager(String cacheName) {
        this(cacheName, CacheType.BOTH);
    }

    protected AbstractStampManager(String cacheName, CacheType cacheType) {
        this(cacheName, cacheType, DEFAULT_EXPIRE);
    }

    protected AbstractStampManager(String cacheName, Duration expire) {
        this(cacheName, CacheType.BOTH, expire);
    }

    protected AbstractStampManager(String cacheName, CacheType cacheType, Duration expire) {
        this.expire = expire;
        this.cache = JetCacheUtils.create(cacheName, cacheType, this.expire);
    }

    /**
     * 指定数据存储缓存
     *
     * @return {@link Cache}
     */
    protected Cache<K, V> getCache() {
        return this.cache;
    }

    @Override
    public Duration getExpire() {
        return this.expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    @Override
    public boolean check(K key, V value) throws StampParameterIllegalException, StampHasExpiredException, StampMismatchException {
        if (ObjectUtils.isEmpty(value)) {
            throw new StampParameterIllegalException("Parameter Stamp value is null");
        }

        V storedStamp = this.get(key);
        if (ObjectUtils.isEmpty(storedStamp)) {
            throw new StampHasExpiredException("Stamp is invalid!");
        }

        if (ObjectUtils.notEqual(storedStamp, value)) {
            throw new StampMismatchException("Stamp is mismathch!");
        }

        return true;
    }

    @Override
    public V get(K key) {
        return this.getCache().get(key);
    }

    @Override
    public void delete(K key) {
        boolean result = this.getCache().remove(key);
        if (!result) {
            log.warn("[PIGXD] |- Delete stamp [{}] failed.", key);
        }
    }

    @Override
    public void put(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        this.getCache().put(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public AutoReleaseLock lock(K key, long expire, TimeUnit timeUnit) {
        return this.getCache().tryLock(key, expire, timeUnit);
    }

    @Override
    public boolean lockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action) {
        return this.getCache().tryLockAndRun(key, expire, timeUnit, action);
    }
}
