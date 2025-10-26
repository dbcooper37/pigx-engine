package com.pigx.engine.jetcache.utils;

import com.pigx.engine.cache.jetcache.enhance.JetCacheCreateCacheFactory;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import java.time.Duration;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/utils/JetCacheUtils.class */
public class JetCacheUtils {
    private static volatile JetCacheUtils instance;
    private JetCacheCreateCacheFactory jetCacheCreateCacheFactory;

    private JetCacheUtils() {
    }

    public static JetCacheUtils getInstance() {
        if (ObjectUtils.isEmpty(instance)) {
            synchronized (JetCacheUtils.class) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = new JetCacheUtils();
                }
            }
        }
        return instance;
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire) {
        return create(name, expire, (Boolean) true);
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue) {
        return create(name, expire, cacheNullValue, (Boolean) null);
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(name, CacheType.BOTH, expire, cacheNullValue, syncLocal);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType) {
        return create(name, cacheType, (Duration) null);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire) {
        return create(name, cacheType, expire, (Boolean) true);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue) {
        return create(name, cacheType, expire, cacheNullValue, null);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return getInstance().getJetCacheCreateCacheFactory().create(name, cacheType, expire, cacheNullValue, syncLocal);
    }

    private void init(JetCacheCreateCacheFactory jetCacheCreateCacheFactory) {
        this.jetCacheCreateCacheFactory = jetCacheCreateCacheFactory;
    }

    private JetCacheCreateCacheFactory getJetCacheCreateCacheFactory() {
        return this.jetCacheCreateCacheFactory;
    }

    public static void setJetCacheCreateCacheFactory(JetCacheCreateCacheFactory jetCacheCreateCacheFactory) {
        getInstance().init(jetCacheCreateCacheFactory);
    }
}
