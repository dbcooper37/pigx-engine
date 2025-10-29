package com.pigx.engine.web.service.secure;

import com.pigx.engine.web.core.annotation.Idempotent;
import com.pigx.engine.web.core.exception.RepeatSubmissionException;
import com.pigx.engine.web.service.stamp.IdempotentStampManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.format.DateTimeParseException;


public class IdempotentHandler {

    private static final Logger log = LoggerFactory.getLogger(AccessLimitedHandler.class);

    public static boolean handle(String key, Idempotent idempotent, String url, IdempotentStampManager idempotentStampManager) {
        // 幂等性校验, 根据缓存中是否存在Token进行校验。
        // 如果缓存中没有Token，通过放行, 同时在缓存中存入Token。
        // 如果缓存中有Token，意味着同一个操作反复操作，认为失败则抛出异常, 并通过统一异常处理返回友好提示
        if (StringUtils.isNotBlank(key)) {
            String token = idempotentStampManager.get(key);
            if (StringUtils.isBlank(token)) {
                Duration configuredDuration = Duration.ZERO;
                String annotationExpire = idempotent.expire();
                if (StringUtils.isNotBlank(annotationExpire)) {
                    try {
                        configuredDuration = Duration.parse(annotationExpire);
                    } catch (DateTimeParseException e) {
                        log.warn("[PIGXD] |- Idempotent duration value is incorrect, on api [{}].", url);
                    }
                }

                if (!configuredDuration.isZero()) {
                    idempotentStampManager.create(key, configuredDuration);
                } else {
                    idempotentStampManager.create(key);
                }

                return true;
            } else {
                throw new RepeatSubmissionException("Don't Repeat Submission");
            }
        }
        return true;
    }
}
