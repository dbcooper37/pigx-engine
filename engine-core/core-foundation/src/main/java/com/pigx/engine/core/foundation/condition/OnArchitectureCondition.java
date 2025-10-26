package com.pigx.engine.core.foundation.condition;

import com.pigx.engine.core.foundation.enums.Architecture;
import java.lang.annotation.Annotation;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/condition/OnArchitectureCondition.class */
class OnArchitectureCondition extends AbstractEnumSpringBootCondition<Architecture> {
    OnArchitectureCondition() {
    }

    @Override // com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnArchitecture.class;
    }
}
