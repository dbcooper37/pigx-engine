package com.pigx.engine.rest.servlet.message.annotation;

import com.pigx.engine.rest.servlet.message.config.RestServletMessageConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestServletMessageConfiguration.class)
public @interface EnableHerodotusRestServletMessage {
}
