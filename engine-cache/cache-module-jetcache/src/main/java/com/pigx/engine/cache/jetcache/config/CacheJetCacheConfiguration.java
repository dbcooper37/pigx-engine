package com.pigx.engine.jetcache.config;

import com.pigx.engine.cache.caffeine.config.CacheCaffeineConfiguration;
import com.pigx.engine.cache.core.properties.CacheProperties;
import com.pigx.engine.cache.jetcache.enhance.HerodotusCacheManager;
import com.pigx.engine.cache.jetcache.enhance.JetCacheCreateCacheFactory;
import com.pigx.engine.cache.jetcache.utils.JetCacheUtils;
import com.pigx.engine.cache.redis.config.CacheRedisConfiguration;
import com.alicp.jetcache.CacheManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@EnableConfigurationProperties({CacheProperties.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({CacheManager.class})
@Import({CacheCaffeineConfiguration.class, CacheRedisConfiguration.class})
/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/config/CacheJetCacheConfiguration.class */
public class CacheJetCacheConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CacheJetCacheConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Cache JetCache] Configure.");
    }

    @Bean
    public JetCacheCreateCacheFactory jetCacheCreateCacheFactory(@Qualifier("jcCacheManager") CacheManager cacheManager, CacheProperties cacheProperties) {
        JetCacheCreateCacheFactory factory = new JetCacheCreateCacheFactory(cacheManager, cacheProperties);
        JetCacheUtils.setJetCacheCreateCacheFactory(factory);
        log.trace("[Herodotus] |- Bean [Jet Cache Create Cache Factory] Configure.");
        return factory;
    }

    @ConditionalOnMissingBean
    @Bean
    @Primary
    public HerodotusCacheManager herodotusCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties) {
        HerodotusCacheManager herodotusCacheManager = new HerodotusCacheManager(jetCacheCreateCacheFactory, cacheProperties);
        herodotusCacheManager.setAllowNullValues(cacheProperties.getAllowNullValues().booleanValue());
        log.trace("[Herodotus] |- Bean [Jet Cache Herodotus Cache Manager] Configure.");
        return herodotusCacheManager;
    }
}
