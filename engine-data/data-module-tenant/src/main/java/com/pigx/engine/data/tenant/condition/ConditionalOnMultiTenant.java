package com.pigx.engine.data.tenant.condition;

import com.pigx.engine.data.tenant.enums.MultiTenant;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnMultiTenantCondition.class)
public @interface ConditionalOnMultiTenant {

    /**
     * {@link MultiTenant} 属性必须配置.
     *
     * @return 预期的算法
     */
    MultiTenant value();
}
