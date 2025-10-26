package com.pigx.engine.message.websocket.servlet.condition;

import com.pigx.engine.core.foundation.context.PropertyResolver;
import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.websocket.servlet.enums.InstanceMode;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/condition/SingleWebSocketInstanceCondition.class */
public class SingleWebSocketInstanceCondition implements Condition {
    private static final Logger log = LoggerFactory.getLogger(SingleWebSocketInstanceCondition.class);

    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        String property = PropertyResolver.getProperty(conditionContext, MessageConstants.ITEM_WEBSOCKET_MULTIPLE_INSTANCE, InstanceMode.SINGLE.name());
        boolean result = Strings.CI.equals(property, InstanceMode.SINGLE.name());
        log.debug("[Herodotus] |- Condition [Single Web Socket Instance] value is [{}]", Boolean.valueOf(result));
        return result;
    }
}
