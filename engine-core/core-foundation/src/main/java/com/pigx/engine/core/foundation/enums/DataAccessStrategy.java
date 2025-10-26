package com.pigx.engine.core.foundation.enums;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/enums/DataAccessStrategy.class */
public enum DataAccessStrategy implements ConditionEnum {
    LOCAL { // from class: com.pigx.engine.core.foundation.enums.DataAccessStrategy.1
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    },
    REMOTE { // from class: com.pigx.engine.core.foundation.enums.DataAccessStrategy.2
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return !LOCAL.isActive(environment);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    }
}
