package com.pigx.engine.logic.upms.annotation;

import com.pigx.engine.logic.upms.config.LogicUpmsConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LogicUpmsConfiguration.class})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/annotation/EnableHerodotusLogicUpms.class */
public @interface EnableHerodotusLogicUpms {
}
