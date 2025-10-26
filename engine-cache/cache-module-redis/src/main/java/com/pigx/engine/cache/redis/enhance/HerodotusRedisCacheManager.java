package com.pigx.engine.redis.enhance;

import com.pigx.engine.cache.core.properties.CacheProperties;
import com.pigx.engine.cache.core.properties.CacheSetting;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

/* loaded from: cache-module-redis-3.5.7.0.jar:cn/herodotus/engine/cache/redis/enhance/HerodotusRedisCacheManager.class */
public class HerodotusRedisCacheManager extends RedisCacheManager {
    private static final Logger log = LoggerFactory.getLogger(HerodotusRedisCacheManager.class);
    private CacheProperties cacheProperties;

    public HerodotusRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, CacheProperties cacheProperties) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheProperties = cacheProperties;
    }

    public HerodotusRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, CacheProperties cacheProperties, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        this.cacheProperties = cacheProperties;
    }

    public HerodotusRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, CacheProperties cacheProperties, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
        this.cacheProperties = cacheProperties;
    }

    public HerodotusRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, CacheProperties cacheProperties) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.cacheProperties = cacheProperties;
    }

    public HerodotusRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        Map<String, CacheSetting> expires = this.cacheProperties.getInstances();
        if (MapUtils.isNotEmpty(expires)) {
            String key = Strings.CS.replace(name, SymbolConstants.COLON, this.cacheProperties.getSeparator());
            if (expires.containsKey(key)) {
                CacheSetting cacheSetting = expires.get(key);
                log.debug("[Herodotus] |- CACHE - Redis cache [{}] is setted to use CUSTEM exprie.", name);
                cacheConfig = cacheConfig.entryTtl(cacheSetting.getExpire());
            }
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
