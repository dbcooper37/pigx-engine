package com.pigx.engine.caffeine.config;

import com.pigx.engine.cache.caffeine.enhance.HerodotusCaffeineCacheManager;
import com.pigx.engine.cache.core.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
/* loaded from: cache-module-caffeine-3.5.7.0.jar:cn/herodotus/engine/cache/caffeine/config/CacheCaffeineConfiguration.class */
public class CacheCaffeineConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CacheCaffeineConfiguration.class);
    private final CacheProperties cacheProperties;

    public CacheCaffeineConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Cache Caffeine] Configure.");
    }

    @Bean
    public Caffeine<Object, Object> caffeine() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(ObjectUtils.isNotEmpty(this.cacheProperties.getLocalExpire()) ? this.cacheProperties.getLocalExpire() : this.cacheProperties.getExpire());
        log.trace("[Herodotus] |- Bean [Caffeine] Configure.");
        return caffeine;
    }

    @ConditionalOnMissingBean({CaffeineCacheManager.class})
    @Bean
    public CaffeineCacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine) {
        HerodotusCaffeineCacheManager herodotusCaffeineCacheManager = new HerodotusCaffeineCacheManager(this.cacheProperties);
        herodotusCaffeineCacheManager.setCaffeine(caffeine);
        log.trace("[Herodotus] |- Bean [Caffeine Cache Manager] Configure.");
        return herodotusCaffeineCacheManager;
    }
}
