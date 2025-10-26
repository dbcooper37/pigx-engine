package com.pigx.engine.web.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.Mapping;

@Target({ElementType.TYPE, ElementType.METHOD})
@Mapping
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/annotation/Crypto.class */
public @interface Crypto {
    boolean requestDecrypt() default true;

    boolean responseEncrypt() default true;
}
