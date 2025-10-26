package com.pigx.engine.data.tenant.condition;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;
import com.pigx.engine.data.tenant.enums.MultiTenant;
import java.lang.annotation.Annotation;

/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/condition/OnMultiTenantCondition.class */
class OnMultiTenantCondition extends AbstractEnumSpringBootCondition<MultiTenant> {
    OnMultiTenantCondition() {
    }

    @Override // com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnMultiTenant.class;
    }
}
