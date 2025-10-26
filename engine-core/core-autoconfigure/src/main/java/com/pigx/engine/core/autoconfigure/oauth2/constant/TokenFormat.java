package com.pigx.engine.autoconfigure.oauth2.constant;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/constant/TokenFormat.class */
public enum TokenFormat implements ConditionEnum {
    JWT { // from class: com.pigx.engine.core.autoconfigure.oauth2.constant.TokenFormat.1
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_AUTHORIZATION_TOKEN_FORMAT);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    },
    OPAQUE { // from class: com.pigx.engine.core.autoconfigure.oauth2.constant.TokenFormat.2
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return !JWT.isActive(environment);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    }
}
