package com.pigx.engine.core.autoconfigure.logging.logstash;

import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(prefix = BaseConstants.PROPERTY_LOG_LOGSTASH, name = BaseConstants.PROPERTY_ENABLED, havingValue = "true")
public @interface ConditionalOnLogstashEnabled {
}
