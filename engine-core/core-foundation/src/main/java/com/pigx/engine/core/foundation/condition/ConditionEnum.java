package com.pigx.engine.core.foundation.condition;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.core.env.Environment;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/condition/ConditionEnum.class */
public interface ConditionEnum {
    boolean isActive(Environment environment);

    String getConstant();

    default String getString(Environment environment, String property) {
        return (String) environment.getProperty(property, String.class);
    }

    default boolean isActive(Environment environment, String property) {
        String value = getString(environment, property);
        return StringUtils.isNotBlank(value) && Strings.CI.equals(value, getConstant());
    }

    default boolean isDefault(Environment environment, String property) {
        String value = getString(environment, property);
        return StringUtils.isBlank(value) || Strings.CI.equals(value, getConstant());
    }
}
