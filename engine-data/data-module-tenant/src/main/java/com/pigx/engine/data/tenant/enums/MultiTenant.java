package com.pigx.engine.data.tenant.enums;

import com.pigx.engine.core.foundation.condition.ConditionEnum;
import com.pigx.engine.data.tenant.constant.TenantConstants;
import org.springframework.core.env.Environment;

/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/enums/MultiTenant.class */
public enum MultiTenant implements ConditionEnum {
    DISCRIMINATOR { // from class: com.pigx.engine.data.tenant.enums.MultiTenant.1
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return isDefault(environment, TenantConstants.ITEM_MULTI_TENANT_APPROACH);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    },
    SCHEMA { // from class: com.pigx.engine.data.tenant.enums.MultiTenant.2
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return isActive(environment, TenantConstants.ITEM_MULTI_TENANT_APPROACH);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    },
    DATABASE { // from class: com.pigx.engine.data.tenant.enums.MultiTenant.3
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return isActive(environment, TenantConstants.ITEM_MULTI_TENANT_APPROACH);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    }
}
