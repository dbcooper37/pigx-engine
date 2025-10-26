package com.pigx.engine.autoconfigure.logging.logstash;

import com.pigx.engine.core.definition.constant.BaseConstants;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = BaseConstants.PROPERTY_LOG_LOGSTASH, name = {BaseConstants.PROPERTY_ENABLED}, havingValue = "true")
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/logging/logstash/ConditionalOnLogstashEnabled.class */
public @interface ConditionalOnLogstashEnabled {
}
