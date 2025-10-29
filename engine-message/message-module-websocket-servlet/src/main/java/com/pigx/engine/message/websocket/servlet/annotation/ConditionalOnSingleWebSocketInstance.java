package com.pigx.engine.message.websocket.servlet.annotation;

import com.pigx.engine.message.websocket.servlet.condition.SingleWebSocketInstanceCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(SingleWebSocketInstanceCondition.class)
public @interface ConditionalOnSingleWebSocketInstance {
}
