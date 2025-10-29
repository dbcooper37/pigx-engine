package com.pigx.engine.oss.dialect.autoconfigure.annotation;

import com.pigx.engine.oss.dialect.autoconfigure.condition.UseMinioDialectCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(UseMinioDialectCondition.class)
public @interface ConditionalOnUseMinioDialect {
}
