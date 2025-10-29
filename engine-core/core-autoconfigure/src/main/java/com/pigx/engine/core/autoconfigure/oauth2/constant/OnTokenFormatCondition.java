package com.pigx.engine.core.autoconfigure.oauth2.constant;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;

import java.lang.annotation.Annotation;


class OnTokenFormatCondition extends AbstractEnumSpringBootCondition<TokenFormat> {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnTokenFormat.class;
    }
}
