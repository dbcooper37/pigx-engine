package com.pigx.engine.data.tenant.enums;

import com.pigx.engine.core.foundation.condition.ConditionEnum;
import com.pigx.engine.data.tenant.constant.TenantConstants;
import org.springframework.core.env.Environment;


public enum MultiTenant implements ConditionEnum {
    /**
     * 共享数据库，独立Schema，共享数据表
     */
    DISCRIMINATOR {
        @Override
        public boolean isActive(Environment environment) {
            return isDefault(environment, TenantConstants.ITEM_MULTI_TENANT_APPROACH);
        }

        @Override
        public String getConstant() {
            return name();
        }
    },
    /**
     * 共享数据库，独立Schema
     */
    SCHEMA {
        @Override
        public boolean isActive(Environment environment) {
            return isActive(environment, TenantConstants.ITEM_MULTI_TENANT_APPROACH);
        }

        @Override
        public String getConstant() {
            return name();
        }
    },
    /**
     * 独立数据库
     */
    DATABASE {
        @Override
        public boolean isActive(Environment environment) {
            return isActive(environment, TenantConstants.ITEM_MULTI_TENANT_APPROACH);
        }

        @Override
        public String getConstant() {
            return name();
        }
    };
}
