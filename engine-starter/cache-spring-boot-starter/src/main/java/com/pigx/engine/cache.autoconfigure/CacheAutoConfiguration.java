package com.pigx.engine.starter.cache;

import cn.herodotus.engine.cache.jetcache.config.CacheJetCacheConfiguration;
import cn.herodotus.engine.cache.redisson.config.CacheRedissonConfiguration;
import cn.herodotus.engine.core.autoconfigure.jackson2.Jackson2AutoConfiguration;
import com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration(
        after = {Jackson2AutoConfiguration.class, JetCacheAutoConfiguration.class}
)
@Import({CacheJetCacheConfiguration.class, CacheRedissonConfiguration.class})
public class CacheAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CacheAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Cache] Configure.");
    }
}

