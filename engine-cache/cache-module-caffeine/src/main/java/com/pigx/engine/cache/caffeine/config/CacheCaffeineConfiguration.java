package com.pigx.engine.cache.caffeine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.pigx.engine.cache.caffeine.enhance.HerodotusCaffeineCacheManager;
import com.pigx.engine.cache.core.properties.CacheProperties;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class CacheCaffeineConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheCaffeineConfiguration.class);

    private final CacheProperties cacheProperties;

    public CacheCaffeineConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Cache Caffeine] Configure.");
    }

    @Bean
    public Caffeine<Object, Object> caffeine() {
        Caffeine<Object, Object> caffeine = Caffeine
                .newBuilder()
                .expireAfterWrite(ObjectUtils.isNotEmpty(cacheProperties.getLocalExpire()) ? cacheProperties.getLocalExpire() : cacheProperties.getExpire());

        log.trace("[PIGXD] |- Bean [Caffeine] Configure.");

        return caffeine;
    }

    @Bean
    @ConditionalOnMissingBean(CaffeineCacheManager.class)
    public CaffeineCacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine) {
        HerodotusCaffeineCacheManager herodotusCaffeineCacheManager = new HerodotusCaffeineCacheManager(cacheProperties);
        herodotusCaffeineCacheManager.setCaffeine(caffeine);
        log.trace("[PIGXD] |- Bean [Caffeine Cache Manager] Configure.");
        return herodotusCaffeineCacheManager;
    }
}
