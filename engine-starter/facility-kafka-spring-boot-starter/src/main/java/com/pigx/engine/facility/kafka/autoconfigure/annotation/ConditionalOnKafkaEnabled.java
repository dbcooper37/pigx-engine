package com.pigx.engine.facility.kafka.autoconfigure.annotation;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.message.core.constants.MessageConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(prefix = MessageConstants.PROPERTY_MESSAGE_KAFKA, name = BaseConstants.PROPERTY_NAME_ENABLED)
public @interface ConditionalOnKafkaEnabled {
}
