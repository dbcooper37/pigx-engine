package com.pigx.engine.core.foundation.condition;

import com.pigx.engine.core.foundation.enums.Architecture;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnArchitectureCondition.class})
/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/condition/ConditionalOnArchitecture.class */
public @interface ConditionalOnArchitecture {
    Architecture value();
}
