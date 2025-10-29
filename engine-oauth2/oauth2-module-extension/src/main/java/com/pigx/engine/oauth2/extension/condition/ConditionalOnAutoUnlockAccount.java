package com.pigx.engine.oauth2.extension.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnAutoUnlockAccountCondition.class)
public @interface ConditionalOnAutoUnlockAccount {
}
