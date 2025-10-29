package com.pigx.engine.oss.dialect.autoconfigure.annotation;

import com.pigx.engine.oss.dialect.autoconfigure.condition.UseS3DialectCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(UseS3DialectCondition.class)
public @interface ConditionalOnUseS3Dialect {
}
