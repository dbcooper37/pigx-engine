package com.pigx.engine.oauth2.extension.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnAutoUnlockAccountCondition.class})
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/condition/ConditionalOnAutoUnlockAccount.class */
public @interface ConditionalOnAutoUnlockAccount {
}
