package com.pigx.engine.data.tenant.condition;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;
import com.pigx.engine.data.tenant.enums.MultiTenant;

import java.lang.annotation.Annotation;


class OnMultiTenantCondition extends AbstractEnumSpringBootCondition<MultiTenant> {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnMultiTenant.class;
    }
}
