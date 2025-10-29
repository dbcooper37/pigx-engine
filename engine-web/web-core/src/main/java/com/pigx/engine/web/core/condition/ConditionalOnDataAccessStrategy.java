package com.pigx.engine.web.core.condition;

import com.pigx.engine.core.foundation.enums.DataAccessStrategy;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnDataAccessStrategyCondition.class)
public @interface ConditionalOnDataAccessStrategy {

    /**
     * {@link DataAccessStrategy} 属性必须配置.
     *
     * @return 预期的算法
     */
    DataAccessStrategy value();
}
