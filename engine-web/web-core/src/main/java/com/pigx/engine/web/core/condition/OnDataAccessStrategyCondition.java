package com.pigx.engine.web.core.condition;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;
import com.pigx.engine.core.foundation.enums.DataAccessStrategy;
import java.lang.annotation.Annotation;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/condition/OnDataAccessStrategyCondition.class */
class OnDataAccessStrategyCondition extends AbstractEnumSpringBootCondition<DataAccessStrategy> {
    OnDataAccessStrategyCondition() {
    }

    @Override // com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnDataAccessStrategy.class;
    }
}
