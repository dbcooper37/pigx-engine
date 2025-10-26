package com.pigx.engine.redisson.annotation;

import com.pigx.engine.cache.redisson.config.CacheRedissonConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CacheRedissonConfiguration.class})
/* loaded from: cache-module-redisson-3.5.7.0.jar:cn/herodotus/engine/cache/redisson/annotation/EnableHerodotusRedisson.class */
public @interface EnableHerodotusRedisson {
}
