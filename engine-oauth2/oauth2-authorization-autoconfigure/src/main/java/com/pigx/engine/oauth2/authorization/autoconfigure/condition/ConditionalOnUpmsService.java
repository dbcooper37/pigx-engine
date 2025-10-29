package com.pigx.engine.oauth2.authorization.autoconfigure.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(IsUpmsServiceCondition.class)
public @interface ConditionalOnUpmsService {
}
