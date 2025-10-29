package com.pigx.engine.cache.jetcache.enhance;

import com.pigx.engine.cache.core.properties.CacheProperties;
import com.pigx.engine.cache.core.properties.CacheSetting;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import java.util.Map;


public class HerodotusCacheManager extends JetCacheSpringCacheManager {

    private static final Logger log = LoggerFactory.getLogger(HerodotusCacheManager.class);

    private final CacheProperties cacheProperties;

    public HerodotusCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties) {
        super(jetCacheCreateCacheFactory);
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.getAllowNullValues());
    }

    public HerodotusCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties, String... cacheNames) {
        super(jetCacheCreateCacheFactory, cacheNames);
        this.cacheProperties = cacheProperties;
    }

    @Override
    protected Cache createJetCache(String name) {
        Map<String, CacheSetting> instances = cacheProperties.getInstances();
        if (MapUtils.isNotEmpty(instances)) {
            String key = Strings.CS.replace(name, SymbolConstants.COLON, cacheProperties.getSeparator());
            if (instances.containsKey(key)) {
                CacheSetting cacheSetting = instances.get(key);
                log.debug("[PIGXD] |- CACHE - Cache [{}] is set to use INSTANCE cache.", name);
                return super.createJetCache(name, cacheSetting);
            }
        }
        return super.createJetCache(name);
    }
}
