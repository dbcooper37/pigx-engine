package com.pigx.engine.core.foundation.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public @interface ConditionalOnServletApplication {
}
