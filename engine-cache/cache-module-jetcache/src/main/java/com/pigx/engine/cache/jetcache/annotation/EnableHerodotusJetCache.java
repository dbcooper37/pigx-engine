package com.pigx.engine.cache.jetcache.annotation;

import com.pigx.engine.cache.jetcache.config.CacheJetCacheConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CacheJetCacheConfiguration.class)
public @interface EnableHerodotusJetCache {
}
