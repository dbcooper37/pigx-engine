package com.pigx.engine.message.websocket.servlet.annotation;

import com.pigx.engine.message.websocket.servlet.config.MessageWebSocketConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Import(MessageWebSocketConfiguration.class)
public @interface EnableHerodotusServletWebSocket {
}
