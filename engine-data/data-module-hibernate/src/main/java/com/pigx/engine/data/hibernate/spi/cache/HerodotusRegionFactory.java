package com.pigx.engine.data.hibernate.spi.cache;

import cn.hutool.v7.extra.spring.SpringUtil;
import java.util.Map;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.cache.spi.support.RegionFactoryTemplate;
import org.hibernate.cache.spi.support.StorageAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.cache.CacheManager;

/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/spi/cache/HerodotusRegionFactory.class */
public class HerodotusRegionFactory extends RegionFactoryTemplate {
    private CacheManager cacheManager;

    protected StorageAccess createQueryResultsRegionStorageAccess(String regionName, SessionFactoryImplementor sessionFactory) {
        return new HerodotusDomainDataStorageAccess(this.cacheManager.getCache(regionName));
    }

    protected StorageAccess createTimestampsRegionStorageAccess(String regionName, SessionFactoryImplementor sessionFactory) {
        return new HerodotusDomainDataStorageAccess(this.cacheManager.getCache(regionName));
    }

    protected DomainDataStorageAccess createDomainDataStorageAccess(DomainDataRegionConfig regionConfig, DomainDataRegionBuildingContext buildingContext) {
        return new HerodotusDomainDataStorageAccess(this.cacheManager.getCache(regionConfig.getRegionName()));
    }

    protected void prepareForUse(SessionFactoryOptions settings, Map configValues) {
        this.cacheManager = (CacheManager) SpringUtil.getBean("herodotusCacheManager", new Object[0]);
    }

    protected void releaseFromUse() {
        this.cacheManager = null;
    }
}
