package com.pigx.engine.jetcache.stamp;

import com.pigx.engine.cache.core.exception.MaximumLimitExceededException;
import cn.hutool.v7.crypto.SecureUtil;
import java.time.Duration;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/* loaded from: cache-module-jetcache-3.5.7.0.jar:cn/herodotus/engine/cache/jetcache/stamp/AbstractCountStampManager.class */
public abstract class AbstractCountStampManager extends AbstractStampManager<String, Long> {
    private int maxTimes;
    private static final Logger log = LoggerFactory.getLogger(AbstractCountStampManager.class);

    protected AbstractCountStampManager(String cacheName, Duration expire) {
        super(cacheName, expire);
        this.maxTimes = 1;
    }

    public int counting(String identity) throws MaximumLimitExceededException {
        return counting(identity, this.maxTimes);
    }

    public int counting(String identity, int maxTimes) throws MaximumLimitExceededException {
        return counting(identity, maxTimes, getExpire());
    }

    public int counting(String identity, int maxTimes, Duration expire) throws MaximumLimitExceededException {
        return counting(identity, maxTimes, expire, false);
    }

    public int counting(String identity, int maxTimes, Duration expire, String function) throws MaximumLimitExceededException {
        return counting(identity, maxTimes, expire, false, function);
    }

    public int counting(String identity, int maxTimes, Duration expire, boolean useMd5) throws MaximumLimitExceededException {
        return counting(identity, maxTimes, expire, useMd5, "AbstractCountStampManager");
    }

    public int counting(String identity, int maxTimes, Duration expire, boolean useMd5, String function) throws MaximumLimitExceededException {
        Assert.notNull(identity, "identity cannot be null");
        String key = useMd5 ? SecureUtil.md5(identity) : identity;
        String expireKey = key + "_expire";
        Long index = get(key);
        if (ObjectUtils.isEmpty(index)) {
            index = 0L;
        }
        if (index.longValue() == 0) {
            if (ObjectUtils.isNotEmpty(expire) && !expire.isZero()) {
                create(key, expire);
                put(expireKey, Long.valueOf(System.currentTimeMillis()), expire);
            } else {
                create(key);
                put(expireKey, Long.valueOf(System.currentTimeMillis()));
            }
        } else {
            Duration newDuration = calculateRemainingTime(expire, expireKey, function);
            put(key, Long.valueOf(index.longValue() + 1), newDuration);
            if (index.longValue() >= maxTimes - 1) {
                throw new MaximumLimitExceededException("Requests are too frequent. Please try again later!");
            }
        }
        long temp = index.longValue() + 1;
        int times = (int) temp;
        log.debug("[Herodotus] |- {} has been recorded [{}] times.", function, Integer.valueOf(times));
        return times;
    }

    private Duration calculateRemainingTime(Duration configuredDuration, String expireKey, String function) {
        Duration duration;
        Long begin = get(expireKey);
        Long current = Long.valueOf(System.currentTimeMillis());
        long interval = current.longValue() - begin.longValue();
        log.debug("[Herodotus] |- {} operation interval [{}] millis.", function, Long.valueOf(interval));
        if (!configuredDuration.isZero()) {
            duration = configuredDuration.minusMillis(interval);
        } else {
            duration = getExpire().minusMillis(interval);
        }
        return duration;
    }

    public int getMaxTimes() {
        return this.maxTimes;
    }

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }
}
