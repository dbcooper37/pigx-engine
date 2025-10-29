package com.pigx.engine.web.service.stamp;

import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.web.core.constant.WebConstants;
import com.pigx.engine.web.service.properties.SecureProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class AccessLimitedStampManager extends AbstractStampManager<String, Long> {

    private static final Logger log = LoggerFactory.getLogger(AccessLimitedStampManager.class);

    private final SecureProperties secureProperties;

    public AccessLimitedStampManager(SecureProperties secureProperties) {
        super(WebConstants.CACHE_NAME_TOKEN_ACCESS_LIMITED, secureProperties.getAccessLimited().getExpire());
        this.secureProperties = secureProperties;
    }

    public SecureProperties getSecureProperties() {
        return secureProperties;
    }

    @Override
    public Long nextStamp(String key) {
        return 1L;
    }

    /**
     * 计算剩余过期时间
     * <p>
     * 每次create或者put，缓存的过期时间都会被覆盖。（注意：Jetcache put 方法的参数名：expireAfterWrite）。
     * 因为Jetcache没有Redis的incr之类的方法，那么每次放入Times值，都会更新过期时间，实际操作下来是变相的延长了过期时间。
     *
     * @param configuredDuration 注解上配置的、且可以正常解析的Duration值
     * @param expireKey          时间标记存储Key值。
     * @return 还剩余的过期时间 {@link Duration}
     */
    public Duration calculateRemainingTime(Duration configuredDuration, String expireKey) {
        Long begin = get(expireKey);
        Long current = System.currentTimeMillis();
        long interval = current - begin;

        log.debug("[PIGXD] |- AccessLimited operation interval [{}] millis.", interval);

        Duration duration;
        if (!configuredDuration.isZero()) {
            duration = configuredDuration.minusMillis(interval);
        } else {
            duration = getExpire().minusMillis(interval);
        }

        return duration;
    }
}
