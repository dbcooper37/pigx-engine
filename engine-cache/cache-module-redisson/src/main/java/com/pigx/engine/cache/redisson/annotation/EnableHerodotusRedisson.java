package com.pigx.engine.cache.redisson.annotation;

import com.pigx.engine.cache.redisson.config.CacheRedissonConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CacheRedissonConfiguration.class)
public @interface EnableHerodotusRedisson {
}
