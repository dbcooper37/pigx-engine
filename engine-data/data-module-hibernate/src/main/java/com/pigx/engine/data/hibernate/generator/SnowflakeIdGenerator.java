package com.pigx.engine.data.hibernate.generator;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 雪花主键ID
 *
 * @author lkhsh
 * @date 2023-07-14
 */
@IdGeneratorType(SnowflakeIdGeneratorType.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface SnowflakeIdGenerator {
}
