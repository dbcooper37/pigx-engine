package com.pigx.engine.core.foundation.condition;

import com.pigx.engine.core.foundation.enums.Architecture;

import java.lang.annotation.Annotation;


class OnArchitectureCondition extends AbstractEnumSpringBootCondition<Architecture> {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnArchitecture.class;
    }
}
