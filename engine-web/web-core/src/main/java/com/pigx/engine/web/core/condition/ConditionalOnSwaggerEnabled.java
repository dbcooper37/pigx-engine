package com.pigx.engine.web.core.condition;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.web.core.constant.WebConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(prefix = WebConstants.PROPERTY_PREFIX_SWAGGER, name = BaseConstants.PROPERTY_ENABLED, havingValue = "true")
public @interface ConditionalOnSwaggerEnabled {
}
