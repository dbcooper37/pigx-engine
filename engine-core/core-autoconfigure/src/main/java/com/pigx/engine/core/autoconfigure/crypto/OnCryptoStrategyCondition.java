package com.pigx.engine.autoconfigure.crypto;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;
import java.lang.annotation.Annotation;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/crypto/OnCryptoStrategyCondition.class */
class OnCryptoStrategyCondition extends AbstractEnumSpringBootCondition<CryptoStrategy> {
    OnCryptoStrategyCondition() {
    }

    @Override // com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnCryptoStrategy.class;
    }
}
