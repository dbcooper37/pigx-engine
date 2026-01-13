package com.pigx.engine.cache.jetcache.warmup;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Configuration properties for cache warmup.
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "herodotus.cache.warmup")
public class CacheWarmupProperties {

    /**
     * Whether cache warmup is enabled on application startup.
     */
    private boolean enabled = true;

    /**
     * Whether to execute warmup asynchronously.
     */
    private boolean async = true;

    /**
     * Timeout in seconds for cache warmup operations.
     */
    private int timeoutSeconds = 300;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("async", async)
                .add("timeoutSeconds", timeoutSeconds)
                .toString();
    }
}
