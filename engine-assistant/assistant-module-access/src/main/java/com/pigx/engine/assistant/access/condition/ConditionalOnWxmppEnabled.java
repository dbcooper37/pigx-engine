package com.pigx.engine.assistant.access.condition;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = AccessConstants.PROPERTY_ACCESS_WXMPP, name = BaseConstants.PROPERTY_ENABLED, havingValue = "true")
public @interface ConditionalOnWxmppEnabled {

}
