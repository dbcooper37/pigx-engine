package com.pigx.engine.data.hibernate.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;

@Target({ElementType.METHOD, ElementType.FIELD})
@IdGeneratorType(SnowflakeIdGeneratorType.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/generator/SnowflakeIdGenerator.class */
public @interface SnowflakeIdGenerator {
}
