package com.pigx.engine.jetcache.annotation;

import com.pigx.engine.cache.jetcache.config.CacheJetCacheConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CacheJetCacheConfiguration.class})
/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/annotation/EnableHerodotusJetCache.class */
public @interface EnableHerodotusJetCache {
}
