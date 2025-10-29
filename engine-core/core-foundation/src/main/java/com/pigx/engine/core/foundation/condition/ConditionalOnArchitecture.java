package com.pigx.engine.core.foundation.condition;

import com.pigx.engine.core.foundation.enums.Architecture;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnArchitectureCondition.class)
public @interface ConditionalOnArchitecture {

    /**
     * {@link Architecture} 属性必须配置.
     *
     * @return 预期的算法
     */
    Architecture value();
}
