package com.pigx.engine.jetcache.enhance;

import com.pigx.engine.cache.core.properties.CacheSetting;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/enhance/JetCacheSpringCacheManager.class */
public class JetCacheSpringCacheManager implements CacheManager {
    private static final Logger log = LoggerFactory.getLogger(JetCacheSpringCacheManager.class);
    private final JetCacheCreateCacheFactory jetCacheCreateCacheFactory;
    private final Map<String, Cache> cacheMap = new ConcurrentHashMap(16);
    private boolean dynamic = true;
    private boolean allowNullValues = true;

    public JetCacheSpringCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory) {
        this.jetCacheCreateCacheFactory = jetCacheCreateCacheFactory;
    }

    public JetCacheSpringCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, String... cacheNames) {
        this.jetCacheCreateCacheFactory = jetCacheCreateCacheFactory;
        setCacheNames(Arrays.asList(cacheNames));
    }

    public boolean isAllowNullValues() {
        return this.allowNullValues;
    }

    public void setAllowNullValues(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    protected Cache createJetCache(String name) {
        com.alicp.jetcache.Cache<Object, Object> cache = this.jetCacheCreateCacheFactory.create(name);
        log.debug("[Herodotus] |- CACHE - Herodotus cache [{}] is CREATED.", name);
        return new JetCacheSpringCache(name, cache, this.allowNullValues);
    }

    protected Cache createJetCache(String name, CacheSetting cacheSetting) {
        com.alicp.jetcache.Cache<Object, Object> cache = this.jetCacheCreateCacheFactory.create(name, Boolean.valueOf(this.allowNullValues), cacheSetting);
        log.debug("[Herodotus] |- CACHE - Herodotus cache [{}] use entity cache is CREATED.", name);
        return new JetCacheSpringCache(name, cache, this.allowNullValues);
    }

    private String availableCacheName(String name) {
        if (Strings.CS.endsWith(name, SymbolConstants.COLON)) {
            return name;
        }
        return name + ":";
    }

    @Nullable
    public Cache getCache(String name) {
        String usedName = availableCacheName(name);
        return this.cacheMap.computeIfAbsent(usedName, cacheName -> {
            if (this.dynamic) {
                return createJetCache(cacheName);
            }
            return null;
        });
    }

    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    private void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(name, createJetCache(name));
            }
            this.dynamic = false;
            return;
        }
        this.dynamic = true;
    }
}
