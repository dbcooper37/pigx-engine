package com.pigx.engine.rest.servlet.upms.annotation;

import com.pigx.engine.rest.servlet.upms.config.RestServletUpmsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestServletUpmsConfiguration.class)
public @interface EnableHerodotusRestServletUpms {
}
