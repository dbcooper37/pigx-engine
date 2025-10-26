package com.pigx.engine.web.core.condition;

import com.pigx.engine.core.foundation.enums.DataAccessStrategy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnDataAccessStrategyCondition.class})
/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/condition/ConditionalOnDataAccessStrategy.class */
public @interface ConditionalOnDataAccessStrategy {
    DataAccessStrategy value();
}
