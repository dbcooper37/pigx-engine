package com.pigx.engine.message.websocket.servlet.condition;

import com.pigx.engine.core.foundation.context.PropertyResolver;
import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.websocket.servlet.enums.InstanceMode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/condition/MultipleWebSocketInstanceCondition.class */
public class MultipleWebSocketInstanceCondition implements Condition {
    private static final Logger log = LoggerFactory.getLogger(MultipleWebSocketInstanceCondition.class);

    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        String property = PropertyResolver.getProperty(conditionContext, MessageConstants.ITEM_WEBSOCKET_MULTIPLE_INSTANCE);
        boolean result = StringUtils.isNotBlank(property) && Strings.CI.equals(property, InstanceMode.MULTIPLE.name());
        log.debug("[Herodotus] |- Condition [Multiple Web Socket Instance] value is [{}]", Boolean.valueOf(result));
        return result;
    }
}
