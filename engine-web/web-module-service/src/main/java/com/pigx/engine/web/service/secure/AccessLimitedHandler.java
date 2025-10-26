package com.pigx.engine.web.service.secure;

import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.core.exception.FrequentRequestsException;
import com.pigx.engine.web.service.stamp.AccessLimitedStampManager;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/secure/AccessLimitedHandler.class */
public class AccessLimitedHandler {
    private static final Logger log = LoggerFactory.getLogger(AccessLimitedHandler.class);

    public static boolean handle(String key, AccessLimited accessLimited, String url, AccessLimitedStampManager accessLimitedStampManager) {
        if (StringUtils.isNotBlank(key)) {
            int maxTimes = accessLimitedStampManager.getSecureProperties().getAccessLimited().getMaxTimes();
            Duration expireDuration = Duration.ZERO;
            int annotationMaxTimes = accessLimited.maxTimes();
            if (annotationMaxTimes != 0) {
                maxTimes = annotationMaxTimes;
            }
            String annotationDuration = accessLimited.duration();
            if (StringUtils.isNotBlank(annotationDuration)) {
                try {
                    expireDuration = Duration.parse(annotationDuration);
                } catch (DateTimeParseException e) {
                    log.warn("[Herodotus] |- AccessLimited duration value is incorrect, on api [{}].", url);
                }
            }
            String expireKey = key + "_expire";
            Long times = accessLimitedStampManager.get(key);
            if (ObjectUtils.isEmpty(times) || times.longValue() == 0) {
                if (!expireDuration.isZero()) {
                    accessLimitedStampManager.create(key, expireDuration);
                    accessLimitedStampManager.put(expireKey, Long.valueOf(System.currentTimeMillis()), expireDuration);
                    return true;
                }
                accessLimitedStampManager.create(key);
                accessLimitedStampManager.put(expireKey, Long.valueOf(System.currentTimeMillis()));
                return true;
            }
            log.debug("[Herodotus] |- AccessLimited request [{}] times.", times);
            if (times.longValue() <= maxTimes) {
                Duration newDuration = accessLimitedStampManager.calculateRemainingTime(expireDuration, expireKey);
                accessLimitedStampManager.put(key, Long.valueOf(times.longValue() + 1), newDuration);
                return true;
            }
            throw new FrequentRequestsException("Requests are too frequent. Please try again later!");
        }
        return true;
    }
}
