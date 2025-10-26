package com.pigx.engine.data.tenant.condition;

import com.pigx.engine.data.tenant.enums.MultiTenant;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnMultiTenantCondition.class})
/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/condition/ConditionalOnMultiTenant.class */
public @interface ConditionalOnMultiTenant {
    MultiTenant value();
}
