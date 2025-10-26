package com.pigx.engine.jetcache.stamp;

import com.pigx.engine.cache.core.exception.StampHasExpiredException;
import com.pigx.engine.cache.core.exception.StampMismatchException;
import com.pigx.engine.cache.core.exception.StampParameterIllegalException;
import com.alicp.jetcache.AutoReleaseLock;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/stamp/StampManager.class */
public interface StampManager<K, V> {
    Duration getExpire();

    void put(K key, V value, long expireAfterWrite, TimeUnit timeUnit);

    V nextStamp(K key);

    boolean check(K key, V value) throws StampParameterIllegalException, StampHasExpiredException, StampMismatchException;

    V get(K key);

    void delete(K key);

    AutoReleaseLock lock(K key, long expire, TimeUnit timeUnit);

    boolean lockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action);

    default void put(K key, V value, Duration expire) {
        put(key, value, expire.toMillis(), TimeUnit.MILLISECONDS);
    }

    default void put(K key, V value) {
        put(key, value, getExpire());
    }

    default V create(K key, long expireAfterWrite, TimeUnit timeUnit) {
        V value = nextStamp(key);
        put(key, value, expireAfterWrite, timeUnit);
        return value;
    }

    default V create(K key, Duration expire) {
        return create(key, expire.toMillis(), TimeUnit.MILLISECONDS);
    }

    default V create(K key) {
        return create(key, getExpire());
    }

    default boolean containKey(K key) {
        V value = get(key);
        return ObjectUtils.isNotEmpty(value);
    }

    default AutoReleaseLock lock(K key, Duration expire) {
        return lock(key, expire.toMillis(), TimeUnit.MILLISECONDS);
    }

    default AutoReleaseLock lock(K key) {
        return lock(key, getExpire());
    }

    default boolean lockAndRun(K key, Duration expire, Runnable action) {
        return lockAndRun(key, expire.toMillis(), TimeUnit.MILLISECONDS, action);
    }

    default boolean lockAndRun(K key, Runnable action) {
        return lockAndRun(key, getExpire(), action);
    }
}
