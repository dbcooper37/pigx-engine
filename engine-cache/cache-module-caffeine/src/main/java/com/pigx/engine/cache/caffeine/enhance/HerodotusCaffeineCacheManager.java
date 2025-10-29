package com.pigx.engine.cache.caffeine.enhance;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.pigx.engine.cache.core.properties.CacheProperties;
import com.pigx.engine.cache.core.properties.CacheSetting;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.Map;


public class HerodotusCaffeineCacheManager extends CaffeineCacheManager {

    private static final Logger log = LoggerFactory.getLogger(HerodotusCaffeineCacheManager.class);

    private final CacheProperties cacheProperties;

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.getAllowNullValues());
    }

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties, String... cacheNames) {
        super(cacheNames);
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.getAllowNullValues());
    }

    @Override
    protected Cache<Object, Object> createNativeCaffeineCache(String name) {
        Map<String, CacheSetting> instances = cacheProperties.getInstances();
        if (MapUtils.isNotEmpty(instances)) {
            String key = Strings.CS.replace(name, SymbolConstants.COLON, cacheProperties.getSeparator());
            if (instances.containsKey(key)) {
                CacheSetting cacheSetting = instances.get(key);
                log.debug("[PIGXD] |- CACHE - Caffeine cache [{}] is set to use INSTANCE config.", name);
                return Caffeine.newBuilder().expireAfterWrite(cacheSetting.getExpire()).build();
            }
        }

        return super.createNativeCaffeineCache(name);
    }
}
