package com.pigx.engine.web.core.condition;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;
import com.pigx.engine.core.foundation.enums.DataAccessStrategy;

import java.lang.annotation.Annotation;


class OnDataAccessStrategyCondition extends AbstractEnumSpringBootCondition<DataAccessStrategy> {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnDataAccessStrategy.class;
    }

}
