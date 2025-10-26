package com.pigx.engine.assistant.access.condition;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.core.definition.constant.BaseConstants;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = AccessConstants.PROPERTY_ACCESS_WXMPP, name = {BaseConstants.PROPERTY_ENABLED}, havingValue = "true")
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/condition/ConditionalOnWxmppEnabled.class */
public @interface ConditionalOnWxmppEnabled {
}
