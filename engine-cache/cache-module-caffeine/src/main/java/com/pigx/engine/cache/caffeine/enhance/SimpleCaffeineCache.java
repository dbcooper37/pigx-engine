package com.pigx.engine.caffeine.enhance;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/* loaded from: cache-module-caffeine-3.5.7.0.jar:cn/herodotus/engine/cache/caffeine/enhance/SimpleCaffeineCache.class */
public class SimpleCaffeineCache {
    private final Cache<String, String> cache;

    public SimpleCaffeineCache(Duration duration) {
        this.cache = Caffeine.newBuilder().expireAfterWrite(duration).build();
    }

    public SimpleCaffeineCache(long duration, TimeUnit unit) {
        this.cache = Caffeine.newBuilder().expireAfterWrite(duration, unit).build();
    }

    public void put(String key, String value) {
        this.cache.put(key, value);
    }

    public String get(String key) {
        return (String) this.cache.getIfPresent(key);
    }
}
