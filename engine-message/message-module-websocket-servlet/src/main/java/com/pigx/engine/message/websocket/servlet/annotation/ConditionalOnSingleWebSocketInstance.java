package com.pigx.engine.message.websocket.servlet.annotation;

import com.pigx.engine.message.websocket.servlet.condition.SingleWebSocketInstanceCondition;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({SingleWebSocketInstanceCondition.class})
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/annotation/ConditionalOnSingleWebSocketInstance.class */
public @interface ConditionalOnSingleWebSocketInstance {
}
