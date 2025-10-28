package com.pigx.engine.rest.servlet.message.annotation;

import com.pigx.engine.rest.servlet.message.config.RestServletMessageConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RestServletMessageConfiguration.class})
/* loaded from: rest-module-servlet-message-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/message/annotation/EnableHerodotusRestServletMessage.class */
public @interface EnableHerodotusRestServletMessage {
}
