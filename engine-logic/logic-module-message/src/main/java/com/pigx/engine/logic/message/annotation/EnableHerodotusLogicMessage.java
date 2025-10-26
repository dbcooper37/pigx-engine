package com.pigx.engine.logic.message.annotation;

import com.pigx.engine.logic.message.config.LogicMessageConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LogicMessageConfiguration.class})
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/annotation/EnableHerodotusLogicMessage.class */
public @interface EnableHerodotusLogicMessage {
}
