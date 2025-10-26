package com.pigx.engine.oauth2.authorization.autoconfigure.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({IsUpmsServiceCondition.class})
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/condition/ConditionalOnUpmsService.class */
public @interface ConditionalOnUpmsService {
}
