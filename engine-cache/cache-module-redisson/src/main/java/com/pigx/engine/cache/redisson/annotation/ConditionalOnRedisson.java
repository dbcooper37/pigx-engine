package com.pigx.engine.redisson.annotation;

import com.pigx.engine.cache.core.constants.CacheConstants;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(value = {CacheConstants.ITEM_REDISSON_ENABLED}, havingValue = "true")
/* loaded from: cache-module-redisson-3.5.7.0.jar:cn/herodotus/engine/cache/redisson/annotation/ConditionalOnRedisson.class */
public @interface ConditionalOnRedisson {
}
