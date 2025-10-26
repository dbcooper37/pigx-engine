package com.pigx.engine.web.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/annotation/AccessLimited.class */
public @interface AccessLimited {
    int maxTimes() default 0;

    String duration() default "";
}
