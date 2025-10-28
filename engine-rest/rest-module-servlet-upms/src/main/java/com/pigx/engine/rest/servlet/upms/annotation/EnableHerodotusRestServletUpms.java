package com.pigx.engine.rest.servlet.upms.annotation;

import com.pigx.engine.rest.servlet.upms.config.RestServletUpmsConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RestServletUpmsConfiguration.class})
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/annotation/EnableHerodotusRestServletUpms.class */
public @interface EnableHerodotusRestServletUpms {
}
