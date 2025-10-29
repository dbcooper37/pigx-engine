package com.pigx.engine.core.autoconfigure.crypto;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnCryptoStrategyCondition.class)
@interface ConditionalOnCryptoStrategy {

    /**
     * {@link CryptoStrategy} 属性必须配置.
     *
     * @return 预期的算法
     */
    CryptoStrategy value();
}
