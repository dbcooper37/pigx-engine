package com.pigx.engine.core.foundation.enums;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.condition.ConditionEnum;
import org.springframework.core.env.Environment;

/**
 * <p> Description : 用于区分是单体应用还是微服务应用 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/26 11:33
 */
public enum Architecture implements ConditionEnum {

    /**
     * 分布式架构
     */
    DISTRIBUTED {
        @Override
        public boolean isActive(Environment environment) {
            return isActive(environment, BaseConstants.ITEM_PLATFORM_ARCHITECTURE);
        }

        @Override
        public String getConstant() {
            return name();
        }
    },

    /**
     * 单体式架构
     */
    MONOCOQUE {
        @Override
        public boolean isActive(Environment environment) {
            return !DISTRIBUTED.isActive(environment);
        }

        @Override
        public String getConstant() {
            return name();
        }
    };
}
