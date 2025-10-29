package com.pigx.engine.oauth2.authorization.autoconfigure.condition;

import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import com.pigx.engine.rest.servlet.upms.config.RestServletUpmsConfiguration;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;


public final class IsUpmsServiceCondition extends AllNestedConditions {

    public IsUpmsServiceCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnClass(RestServletUpmsConfiguration.class)
    static final class OnRestServletService {

    }

    @ConditionalOnServletApplication
    static final class OnServletApplication {

    }
}
