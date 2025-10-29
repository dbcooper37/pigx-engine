package com.pigx.engine.cache.jetcache.enhance;

import com.pigx.engine.cache.core.properties.CacheSetting;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JetCacheSpringCacheManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(JetCacheSpringCacheManager.class);
    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
    private final JetCacheCreateCacheFactory jetCacheCreateCacheFactory;
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
        return allowNullValues;
    }

    public void setAllowNullValues(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    protected Cache createJetCache(String name) {
        com.alicp.jetcache.Cache<Object, Object> cache = jetCacheCreateCacheFactory.create(name);
        log.debug("[PIGXD] |- CACHE - Herodotus cache [{}] is CREATED.", name);
        return new JetCacheSpringCache(name, cache, allowNullValues);
    }

    protected Cache createJetCache(String name, CacheSetting cacheSetting) {
        com.alicp.jetcache.Cache<Object, Object> cache = jetCacheCreateCacheFactory.create(name, allowNullValues, cacheSetting);
        log.debug("[PIGXD] |- CACHE - Herodotus cache [{}] use entity cache is CREATED.", name);
        return new JetCacheSpringCache(name, cache, allowNullValues);
    }

    private String availableCacheName(String name) {
        if (Strings.CS.endsWith(name, SymbolConstants.COLON)) {
            return name;
        } else {
            return name + SymbolConstants.COLON;
        }
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        String usedName = availableCacheName(name);
        return this.cacheMap.computeIfAbsent(usedName, cacheName ->
                this.dynamic ? createJetCache(cacheName) : null);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    private void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(name, createJetCache(name));
            }
            this.dynamic = false;
        } else {
            this.dynamic = true;
        }
    }
}
