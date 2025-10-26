package com.pigx.engine.web.service.stamp;

import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.web.core.constant.WebConstants;
import com.pigx.engine.web.service.properties.SecureProperties;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/stamp/AccessLimitedStampManager.class */
public class AccessLimitedStampManager extends AbstractStampManager<String, Long> {
    private static final Logger log = LoggerFactory.getLogger(AccessLimitedStampManager.class);
    private final SecureProperties secureProperties;

    public AccessLimitedStampManager(SecureProperties secureProperties) {
        super(WebConstants.CACHE_NAME_TOKEN_ACCESS_LIMITED, secureProperties.getAccessLimited().getExpire());
        this.secureProperties = secureProperties;
    }

    public SecureProperties getSecureProperties() {
        return this.secureProperties;
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public Long nextStamp(String key) {
        return 1L;
    }

    public Duration calculateRemainingTime(Duration configuredDuration, String expireKey) {
        Duration duration;
        Long begin = get(expireKey);
        Long current = Long.valueOf(System.currentTimeMillis());
        long interval = current.longValue() - begin.longValue();
        log.debug("[Herodotus] |- AccessLimited operation interval [{}] millis.", Long.valueOf(interval));
        if (!configuredDuration.isZero()) {
            duration = configuredDuration.minusMillis(interval);
        } else {
            duration = getExpire().minusMillis(interval);
        }
        return duration;
    }
}
