package com.pigx.engine.message.websocket.servlet.annotation;

import com.pigx.engine.message.websocket.servlet.config.MessageWebSocketConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MessageWebSocketConfiguration.class})
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/annotation/EnableHerodotusServletWebSocket.class */
public @interface EnableHerodotusServletWebSocket {
}
