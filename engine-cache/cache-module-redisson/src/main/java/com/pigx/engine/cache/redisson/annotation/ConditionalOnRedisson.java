package com.pigx.engine.cache.redisson.annotation;

import com.pigx.engine.cache.core.constants.CacheConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(value = CacheConstants.ITEM_REDISSON_ENABLED, havingValue = "true")
public @interface ConditionalOnRedisson {
}
