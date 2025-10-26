package com.pigx.engine.redis.annotation;

import com.pigx.engine.cache.redis.config.CacheRedisConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CacheRedisConfiguration.class})
/* loaded from: cache-module-redis-3.5.7.0.jar:cn/herodotus/engine/cache/redis/annotation/EnableHerodotusRedis.class */
public @interface EnableHerodotusRedis {
}
