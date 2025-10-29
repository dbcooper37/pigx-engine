package com.pigx.engine.core.autoconfigure.crypto;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;

import java.lang.annotation.Annotation;


class OnCryptoStrategyCondition extends AbstractEnumSpringBootCondition<CryptoStrategy> {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnCryptoStrategy.class;
    }
}
