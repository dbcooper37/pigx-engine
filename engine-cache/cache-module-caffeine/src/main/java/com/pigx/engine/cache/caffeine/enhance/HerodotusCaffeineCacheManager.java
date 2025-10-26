package com.pigx.engine.caffeine.enhance;

import com.pigx.engine.cache.core.properties.CacheProperties;
import com.pigx.engine.cache.core.properties.CacheSetting;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;

/* loaded from: cache-module-caffeine-3.5.7.0.jar:cn/herodotus/engine/cache/caffeine/enhance/HerodotusCaffeineCacheManager.class */
public class HerodotusCaffeineCacheManager extends CaffeineCacheManager {
    private static final Logger log = LoggerFactory.getLogger(HerodotusCaffeineCacheManager.class);
    private final CacheProperties cacheProperties;

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
        setAllowNullValues(cacheProperties.getAllowNullValues().booleanValue());
    }

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties, String... cacheNames) {
        super(cacheNames);
        this.cacheProperties = cacheProperties;
        setAllowNullValues(cacheProperties.getAllowNullValues().booleanValue());
    }

    protected Cache<Object, Object> createNativeCaffeineCache(String name) {
        Map<String, CacheSetting> instances = this.cacheProperties.getInstances();
        if (MapUtils.isNotEmpty(instances)) {
            String key = Strings.CS.replace(name, SymbolConstants.COLON, this.cacheProperties.getSeparator());
            if (instances.containsKey(key)) {
                CacheSetting cacheSetting = instances.get(key);
                log.debug("[Herodotus] |- CACHE - Caffeine cache [{}] is set to use INSTANCE config.", name);
                return Caffeine.newBuilder().expireAfterWrite(cacheSetting.getExpire()).build();
            }
        }
        return super.createNativeCaffeineCache(name);
    }
}
