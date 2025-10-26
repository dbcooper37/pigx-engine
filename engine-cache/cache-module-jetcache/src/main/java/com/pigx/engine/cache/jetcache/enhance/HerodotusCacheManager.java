package com.pigx.engine.jetcache.enhance;

import com.pigx.engine.cache.core.properties.CacheProperties;
import com.pigx.engine.cache.core.properties.CacheSetting;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/enhance/HerodotusCacheManager.class */
public class HerodotusCacheManager extends JetCacheSpringCacheManager {
    private static final Logger log = LoggerFactory.getLogger(HerodotusCacheManager.class);
    private final CacheProperties cacheProperties;

    public HerodotusCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties) {
        super(jetCacheCreateCacheFactory);
        this.cacheProperties = cacheProperties;
        setAllowNullValues(cacheProperties.getAllowNullValues().booleanValue());
    }

    public HerodotusCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties, String... cacheNames) {
        super(jetCacheCreateCacheFactory, cacheNames);
        this.cacheProperties = cacheProperties;
    }

    @Override // com.pigx.engine.cache.jetcache.enhance.JetCacheSpringCacheManager
    protected Cache createJetCache(String name) {
        Map<String, CacheSetting> instances = this.cacheProperties.getInstances();
        if (MapUtils.isNotEmpty(instances)) {
            String key = Strings.CS.replace(name, SymbolConstants.COLON, this.cacheProperties.getSeparator());
            if (instances.containsKey(key)) {
                CacheSetting cacheSetting = instances.get(key);
                log.debug("[Herodotus] |- CACHE - Cache [{}] is set to use INSTANCE cache.", name);
                return super.createJetCache(name, cacheSetting);
            }
        }
        return super.createJetCache(name);
    }
}
