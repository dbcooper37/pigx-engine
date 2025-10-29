package com.pigx.engine.core.foundation.enums;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;


public enum DataAccessStrategy implements ConditionEnum {

    /**
     * 目标为服务本地
     */
    LOCAL {
        @Override
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY);
        }

        @Override
        public String getConstant() {
            return name();
        }
    },

    /**
     * 目标为远程访问
     */
    REMOTE {
        @Override
        public boolean isActive(Environment environment) {
            return !LOCAL.isActive(environment);
        }

        @Override
        public String getConstant() {
            return name();
        }
    };
}
