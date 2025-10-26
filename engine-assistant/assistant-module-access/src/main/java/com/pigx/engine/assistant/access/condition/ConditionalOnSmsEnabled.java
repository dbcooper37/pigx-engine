package com.pigx.engine.assistant.access.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = "sms", name = {"config-type"})
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/condition/ConditionalOnSmsEnabled.class */
public @interface ConditionalOnSmsEnabled {
}
