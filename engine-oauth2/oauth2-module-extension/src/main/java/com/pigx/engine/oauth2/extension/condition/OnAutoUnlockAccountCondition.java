package com.pigx.engine.oauth2.extension.condition;

import com.pigx.engine.core.foundation.context.PropertyResolver;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class OnAutoUnlockAccountCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(OnAutoUnlockAccountCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        boolean result = PropertyResolver.getBoolean(conditionContext, OAuth2Constants.ITEM_SIGN_IN_FAILURE_LIMITED_AUTO_UNLOCK, true);
        log.debug("[PIGXD] |- Condition [Auto Unlock User Account] value is [{}]", result);
        return result;
    }
}
