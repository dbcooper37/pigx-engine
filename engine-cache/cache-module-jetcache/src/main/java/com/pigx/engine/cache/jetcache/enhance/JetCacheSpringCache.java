package com.pigx.engine.jetcache.enhance;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.alicp.jetcache.Cache;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.lang.Nullable;

/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/enhance/JetCacheSpringCache.class */
public class JetCacheSpringCache extends AbstractValueAdaptingCache {
    private static final Logger log = LoggerFactory.getLogger(JetCacheSpringCache.class);
    private final String cacheName;
    private final Cache<Object, Object> cache;

    public JetCacheSpringCache(String cacheName, Cache<Object, Object> cache, boolean allowNullValues) {
        super(allowNullValues);
        this.cacheName = cacheName;
        this.cache = cache;
    }

    public String getName() {
        return this.cacheName;
    }

    /* renamed from: getNativeCache, reason: merged with bridge method [inline-methods] */
    public final Cache<Object, Object> m30getNativeCache() {
        return this.cache;
    }

    @Nullable
    protected Object lookup(Object key) {
        Object value = this.cache.get(key);
        if (ObjectUtils.isNotEmpty(value)) {
            log.trace("[Herodotus] |- CACHE - Lookup data in herodotus cache, value is : [{}]", Jackson2Utils.toJson(value));
            return value;
        }
        return null;
    }

    @Nullable
    public <T> T get(Object obj, Callable<T> callable) {
        log.trace("[Herodotus] |- CACHE - Get data in herodotus cache, key: {}", obj);
        return (T) fromStoreValue(this.cache.computeIfAbsent(obj, k -> {
            try {
                return toStoreValue(callable.call());
            } catch (Throwable ex) {
                throw new Cache.ValueRetrievalException(obj, callable, ex);
            }
        }));
    }

    @Nullable
    public void put(Object key, @Nullable Object value) {
        log.trace("[Herodotus] |- CACHE - Put data in herodotus cache, key: {}", key);
        this.cache.put(key, toStoreValue(value));
    }

    @Nullable
    public Cache.ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        log.trace("[Herodotus] |- CACHE - PutIfPresent data in herodotus cache, key: {}", key);
        Object existing = Boolean.valueOf(this.cache.putIfAbsent(key, toStoreValue(value)));
        return toValueWrapper(existing);
    }

    public void evict(Object key) {
        log.trace("[Herodotus] |- CACHE - Evict data in herodotus cache, key: {}", key);
        this.cache.remove(key);
    }

    public boolean evictIfPresent(Object key) {
        log.trace("[Herodotus] |- CACHE - EvictIfPresent data in herodotus cache, key: {}", key);
        return this.cache.remove(key);
    }

    public void clear() {
        log.trace("[Herodotus] |- CACHE - Clear data in herodotus cache.");
        this.cache.close();
    }
}
