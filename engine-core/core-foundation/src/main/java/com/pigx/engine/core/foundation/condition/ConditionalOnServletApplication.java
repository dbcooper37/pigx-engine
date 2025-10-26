package com.pigx.engine.core.foundation.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/condition/ConditionalOnServletApplication.class */
public @interface ConditionalOnServletApplication {
}
