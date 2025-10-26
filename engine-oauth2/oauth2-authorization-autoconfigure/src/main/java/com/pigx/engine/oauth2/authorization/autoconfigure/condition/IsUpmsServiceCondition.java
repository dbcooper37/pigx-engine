package com.pigx.engine.oauth2.authorization.autoconfigure.condition;

import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import com.pigx.engine.rest.servlet.upms.config.RestServletUpmsConfiguration;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ConfigurationCondition;

/* JADX WARN: Classes with same name are omitted:
  
 */
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/condition/IsUpmsServiceCondition.class */
public final class IsUpmsServiceCondition extends AllNestedConditions {
    public IsUpmsServiceCondition() {
        super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
    }

    /* JADX WARN: Classes with same name are omitted:
  
 */
    @ConditionalOnClass({RestServletUpmsConfiguration.class})
    /* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/condition/IsUpmsServiceCondition$OnRestServletService.class */
    static final class OnRestServletService {
        OnRestServletService() {
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  
 */
    @ConditionalOnServletApplication
    /* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/condition/IsUpmsServiceCondition$OnServletApplication.class */
    static final class OnServletApplication {
        OnServletApplication() {
        }
    }
}
