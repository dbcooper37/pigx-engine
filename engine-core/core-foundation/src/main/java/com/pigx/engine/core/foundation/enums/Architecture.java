package com.pigx.engine.core.foundation.enums;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/enums/Architecture.class */
public enum Architecture implements ConditionEnum {
    DISTRIBUTED { // from class: com.pigx.engine.core.foundation.enums.Architecture.1
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_PLATFORM_ARCHITECTURE);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    },
    MONOCOQUE { // from class: com.pigx.engine.core.foundation.enums.Architecture.2
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return !DISTRIBUTED.isActive(environment);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    }
}
