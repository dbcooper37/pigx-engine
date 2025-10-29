package com.pigx.engine.oss.dialect.autoconfigure.condition;

import com.pigx.engine.core.foundation.context.PropertyResolver;
import com.pigx.engine.oss.dialect.core.constants.OssConstants;
import com.pigx.engine.oss.dialect.core.enums.Dialect;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class UseS3DialectCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(UseS3DialectCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String property = PropertyResolver.getProperty(context, OssConstants.ITEM_OSS_DIALECT);
        boolean result = Strings.CI.equals(property, Dialect.S3.name());
        log.debug("[Herodotus] |- Condition [Use Amazon S3 Dialect] value is [{}]", result);
        return result;
    }
}
