package com.pigx.engine.web.core.condition;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.web.core.constant.WebConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = WebConstants.PROPERTY_SERVICE_REST_SCAN, name = BaseConstants.PROPERTY_ENABLED, matchIfMissing = true)
public @interface ConditionalOnRestScanEnabled {
}
