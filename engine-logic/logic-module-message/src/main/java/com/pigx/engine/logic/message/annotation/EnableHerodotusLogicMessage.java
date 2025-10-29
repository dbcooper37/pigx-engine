package com.pigx.engine.logic.message.annotation;

import com.pigx.engine.logic.message.config.LogicMessageConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogicMessageConfiguration.class)
public @interface EnableHerodotusLogicMessage {
}
