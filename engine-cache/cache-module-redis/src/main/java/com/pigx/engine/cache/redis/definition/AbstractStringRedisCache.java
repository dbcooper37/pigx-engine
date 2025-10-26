package com.pigx.engine.redis.definition;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;

/* loaded from: cache-module-redis-3.5.7.0.jar:cn/herodotus/engine/cache/redis/definition/AbstractStringRedisCache.class */
public abstract class AbstractStringRedisCache implements StringRedisCache {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(10);
    private static final String DEFAULT_PREFIX = "cache:simple:default:";
    private StringRedisTemplate stringRedisTemplate;
    private String prefix = DEFAULT_PREFIX;
    private long defaultTimeout = DEFAULT_TIMEOUT.toMillis();

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDefaultTimeout(long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    @Override // com.pigx.engine.cache.redis.definition.StringRedisCache
    public void cache(String key, String value) {
        cache(key, value, this.defaultTimeout);
    }

    @Override // com.pigx.engine.cache.redis.definition.StringRedisCache
    public void cache(String key, String value, long timeout) {
        this.stringRedisTemplate.opsForValue().set(this.prefix + key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override // com.pigx.engine.cache.redis.definition.StringRedisCache
    public String get(String key) {
        return (String) this.stringRedisTemplate.opsForValue().get(this.prefix + key);
    }

    @Override // com.pigx.engine.cache.redis.definition.StringRedisCache
    public boolean containsKey(String key) {
        Long expire = this.stringRedisTemplate.getExpire(this.prefix + key, TimeUnit.MILLISECONDS);
        if (expire == null) {
            expire = 0L;
        }
        return expire.longValue() > 0;
    }

    @Override // com.pigx.engine.cache.redis.definition.StringRedisCache
    public boolean delete(String key) {
        if (containsKey(key)) {
            return this.stringRedisTemplate.delete(key).booleanValue();
        }
        return true;
    }
}
