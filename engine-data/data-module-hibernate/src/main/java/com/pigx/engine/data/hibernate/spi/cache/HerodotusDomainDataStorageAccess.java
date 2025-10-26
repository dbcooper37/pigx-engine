package com.pigx.engine.data.hibernate.spi.cache;

import com.pigx.engine.core.foundation.context.TenantContextHolder;
import cn.hutool.v7.crypto.SecureUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cache.spi.QueryKey;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/spi/cache/HerodotusDomainDataStorageAccess.class */
public class HerodotusDomainDataStorageAccess implements DomainDataStorageAccess {
    private static final Logger log = LoggerFactory.getLogger(HerodotusDomainDataStorageAccess.class);
    private Cache cache;

    public HerodotusDomainDataStorageAccess() {
    }

    public HerodotusDomainDataStorageAccess(Cache cache) {
        this.cache = cache;
    }

    private String secure(Object key) {
        if (key instanceof QueryKey) {
            QueryKey queryKey = (QueryKey) key;
            int hashCode = queryKey.hashCode();
            String hashCodeString = String.valueOf(hashCode);
            String secureKey = SecureUtil.md5(hashCodeString);
            log.trace("[Herodotus] |- SPI - Convert query key hashcode [{}] to secureKey [{}]", Integer.valueOf(hashCode), secureKey);
            return secureKey;
        }
        return String.valueOf(key);
    }

    private String getTenantId() {
        String tenantId = TenantContextHolder.getTenantId();
        log.trace("[Herodotus] |- SPI - Tenant identifier for jpa second level cache is : [{}]", tenantId);
        return StringUtils.toRootLowerCase(tenantId);
    }

    private String wrapper(Object key) {
        String original = secure(key);
        String tenantId = getTenantId();
        String result = tenantId + ":" + original;
        log.trace("[Herodotus] |- SPI - Current cache key is : [{}]", result);
        return result;
    }

    private Object get(Object key) {
        Cache.ValueWrapper value = this.cache.get(key);
        if (ObjectUtils.isNotEmpty(value)) {
            return value.get();
        }
        return null;
    }

    public boolean contains(Object key) {
        String wrapperKey = wrapper(key);
        Object value = get(wrapperKey);
        log.trace("[Herodotus] |- SPI - check is key : [{}] exist.", wrapperKey);
        return ObjectUtils.isNotEmpty(value);
    }

    public Object getFromCache(Object key, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        Object value = get(wrapperKey);
        log.trace("[Herodotus] |- SPI - get from cache key is : [{}], value is : [{}]", wrapperKey, value);
        return value;
    }

    public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        log.trace("[Herodotus] |- SPI - put into cache key is : [{}], value is : [{}]", wrapperKey, value);
        this.cache.put(wrapperKey, value);
    }

    public void removeFromCache(Object key, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        log.trace("[Herodotus] |- SPI - remove from cache key is : [{}]", wrapperKey);
        this.cache.evict(wrapperKey);
    }

    public void evictData(Object key) {
        String wrapperKey = wrapper(key);
        log.trace("[Herodotus] |- SPI - evict key : [{}] from cache.", wrapperKey);
        this.cache.evict(wrapperKey);
    }

    public void clearCache(SharedSessionContractImplementor session) {
        evictData();
    }

    public void evictData() {
        this.cache.clear();
    }

    public void release() {
        this.cache.invalidate();
    }
}
