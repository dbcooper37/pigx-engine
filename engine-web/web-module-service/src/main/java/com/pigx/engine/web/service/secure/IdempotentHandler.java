package com.pigx.engine.web.service.secure;

import com.pigx.engine.web.core.annotation.Idempotent;
import com.pigx.engine.web.core.exception.RepeatSubmissionException;
import com.pigx.engine.web.service.stamp.IdempotentStampManager;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/secure/IdempotentHandler.class */
public class IdempotentHandler {
    private static final Logger log = LoggerFactory.getLogger(AccessLimitedHandler.class);

    public static boolean handle(String key, Idempotent idempotent, String url, IdempotentStampManager idempotentStampManager) {
        if (StringUtils.isNotBlank(key)) {
            String token = idempotentStampManager.get(key);
            if (StringUtils.isBlank(token)) {
                Duration configuredDuration = Duration.ZERO;
                String annotationExpire = idempotent.expire();
                if (StringUtils.isNotBlank(annotationExpire)) {
                    try {
                        configuredDuration = Duration.parse(annotationExpire);
                    } catch (DateTimeParseException e) {
                        log.warn("[Herodotus] |- Idempotent duration value is incorrect, on api [{}].", url);
                    }
                }
                if (!configuredDuration.isZero()) {
                    idempotentStampManager.create(key, configuredDuration);
                    return true;
                }
                idempotentStampManager.create(key);
                return true;
            }
            throw new RepeatSubmissionException("Don't Repeat Submission");
        }
        return true;
    }
}
