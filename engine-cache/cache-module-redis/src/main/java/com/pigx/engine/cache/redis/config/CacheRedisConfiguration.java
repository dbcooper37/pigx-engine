package com.pigx.engine.redis.config;

import com.pigx.engine.cache.core.properties.CacheProperties;
import com.pigx.engine.cache.redis.enhance.HerodotusRedisCacheManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableConfigurationProperties({CacheProperties.class})
@Configuration(proxyBeanMethods = false)
/* loaded from: cache-module-redis-3.5.7.0.jar:cn/herodotus/engine/cache/redis/config/CacheRedisConfiguration.class */
public class CacheRedisConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CacheRedisConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Cache Redis] Configure.");
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        log.trace("[Herodotus] |- Bean [Jackson2Json Redis Serializer] Configure.");
        return jackson2JsonRedisSerializer;
    }

    @ConditionalOnMissingBean
    @Bean(name = {"redisTemplate"})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        redisTemplate.setDefaultSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();
        log.trace("[Herodotus] |- Bean [Redis Template] Configure.");
        return redisTemplate;
    }

    @ConditionalOnMissingBean
    @Bean(name = {"stringRedisTemplate"})
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();
        log.trace("[Herodotus] |- Bean [String Redis Template] Configure.");
        return stringRedisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        log.trace("[Herodotus] |- Bean [Redis Message Listener Container] Configure.");
        return container;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, CacheProperties cacheProperties) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(cacheProperties.getExpire());
        boolean allowNullValues = cacheProperties.getAllowNullValues().booleanValue();
        if (!allowNullValues) {
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }
        HerodotusRedisCacheManager herodotusRedisCacheManager = new HerodotusRedisCacheManager(redisCacheWriter, redisCacheConfiguration, cacheProperties);
        herodotusRedisCacheManager.setTransactionAware(false);
        herodotusRedisCacheManager.afterPropertiesSet();
        log.trace("[Herodotus] |- Bean [Redis Cache Manager] Configure.");
        return herodotusRedisCacheManager;
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan({"com.pigx.engine.cache.redis.utils"})
    /* loaded from: cache-module-redis-3.5.7.0.jar:cn/herodotus/engine/cache/redis/config/CacheRedisConfiguration$RedisUtilsConfiguration.class */
    static class RedisUtilsConfiguration {
        RedisUtilsConfiguration() {
        }

        @PostConstruct
        public void postConstruct() {
            CacheRedisConfiguration.log.debug("[Herodotus] |- Module [Cache Redis Utils] Configure.");
        }
    }
}
