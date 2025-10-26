package com.pigx.engine.oauth2.persistence.sas.jpa.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;

@Target({ElementType.FIELD, ElementType.METHOD})
@IdGeneratorType(HerodotusAuthorizationIdGeneratorType.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/generator/HerodotusAuthorizationIdGenerator.class */
public @interface HerodotusAuthorizationIdGenerator {
}
