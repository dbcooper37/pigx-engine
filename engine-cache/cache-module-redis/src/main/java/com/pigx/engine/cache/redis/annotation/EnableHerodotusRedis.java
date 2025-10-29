package com.pigx.engine.cache.redis.annotation;

import com.pigx.engine.cache.redis.config.CacheRedisConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CacheRedisConfiguration.class)
public @interface EnableHerodotusRedis {
}
