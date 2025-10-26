package com.pigx.engine.autoconfigure.crypto;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/crypto/CryptoStrategy.class */
public enum CryptoStrategy implements ConditionEnum {
    STANDARD { // from class: com.pigx.engine.core.autoconfigure.crypto.CryptoStrategy.1
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_CRYPTO_STRATEGY);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    },
    SM { // from class: com.pigx.engine.core.autoconfigure.crypto.CryptoStrategy.2
        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public boolean isActive(Environment environment) {
            return !STANDARD.isActive(environment);
        }

        @Override // com.pigx.engine.core.foundation.condition.ConditionEnum
        public String getConstant() {
            return name();
        }
    }
}
