package com.pigx.engine.jetcache.enhance;

import cn.hutool.v7.extra.spring.SpringUtil;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/enhance/HerodotusMybatisCache.class */
public class HerodotusMybatisCache implements Cache {
    private static final Logger log = LoggerFactory.getLogger(HerodotusMybatisCache.class);
    private final String id;
    private final com.alicp.jetcache.Cache<Object, Object> cache;
    private final AtomicInteger counter = new AtomicInteger(0);

    public HerodotusMybatisCache(String id) {
        this.id = id;
        JetCacheCreateCacheFactory jetCacheCreateCacheFactory = (JetCacheCreateCacheFactory) SpringUtil.getBean("jetCacheCreateCacheFactory", new Object[0]);
        this.cache = jetCacheCreateCacheFactory.create(this.id);
    }

    public String getId() {
        return this.id;
    }

    public void putObject(Object key, Object value) {
        this.cache.put(key, value);
        this.counter.incrementAndGet();
        log.debug("[Herodotus] |- CACHE - Put data into Mybatis Cache, with key: [{}]", key);
    }

    public Object getObject(Object key) {
        Object obj = this.cache.get(key);
        log.debug("[Herodotus] |- CACHE - Get data from Mybatis Cache, with key: [{}]", key);
        return obj;
    }

    public Object removeObject(Object key) {
        Object obj = Boolean.valueOf(this.cache.remove(key));
        this.counter.decrementAndGet();
        log.debug("[Herodotus] |- CACHE - Remove data from Mybatis Cache, with key: [{}]", key);
        return obj;
    }

    public void clear() {
        this.cache.close();
        log.debug("[Herodotus] |- CACHE - Clear Mybatis Cache.");
    }

    public int getSize() {
        return this.counter.get();
    }
}
