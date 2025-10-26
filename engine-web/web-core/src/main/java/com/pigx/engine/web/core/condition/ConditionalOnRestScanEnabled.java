package com.pigx.engine.web.core.condition;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.web.core.constant.WebConstants;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = WebConstants.PROPERTY_SERVICE_REST_SCAN, name = {BaseConstants.PROPERTY_ENABLED}, matchIfMissing = true)
/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/condition/ConditionalOnRestScanEnabled.class */
public @interface ConditionalOnRestScanEnabled {
}
