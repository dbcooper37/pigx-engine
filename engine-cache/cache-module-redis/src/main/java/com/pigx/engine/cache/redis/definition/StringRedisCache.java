package com.pigx.engine.redis.definition;

import org.springframework.beans.factory.InitializingBean;

/* loaded from: cache-module-redis-3.5.7.0.jar:cn/herodotus/engine/cache/redis/definition/StringRedisCache.class */
public interface StringRedisCache extends InitializingBean {
    void cache(String key, String value);

    void cache(String key, String value, long timeout);

    String get(String key);

    boolean containsKey(String key);

    boolean delete(String key);
}
