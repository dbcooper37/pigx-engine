package com.pigx.engine.core.autoconfigure.crypto;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;


public enum CryptoStrategy implements ConditionEnum {

    /**
     * 传统加密算法，RSA AES 等
     */
    STANDARD {
        @Override
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_CRYPTO_STRATEGY);
        }

        @Override
        public String getConstant() {
            return name();
        }
    },

    /**
     * 国密加密算法
     */
    SM {
        @Override
        public boolean isActive(Environment environment) {
            return !STANDARD.isActive(environment);
        }

        @Override
        public String getConstant() {
            return name();
        }
    };
}
