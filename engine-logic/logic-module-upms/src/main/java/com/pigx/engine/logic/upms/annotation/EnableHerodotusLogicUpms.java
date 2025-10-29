package com.pigx.engine.logic.upms.annotation;

import com.pigx.engine.logic.upms.config.LogicUpmsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogicUpmsConfiguration.class)
public @interface EnableHerodotusLogicUpms {
}
