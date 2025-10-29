package com.pigx.engine.core.autoconfigure.oauth2.constant;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;


public enum TokenFormat implements ConditionEnum {

    /**
     * 一种自包含的、无状态的令牌。无需服务端验证。
     */
    JWT {
        @Override
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_AUTHORIZATION_TOKEN_FORMAT);
        }

        @Override
        public String getConstant() {
            return name();
        }
    },

    /**
     * 不透明令牌。需要发送回授权服务器进行验证
     */
    OPAQUE {
        @Override
        public boolean isActive(Environment environment) {
            return !JWT.isActive(environment);
        }

        @Override
        public String getConstant() {
            return name();
        }
    };
}
