package com.pigx.engine.caffeine.enhance;

import com.github.benmanes.caffeine.cache.Expiry;

/* loaded from: cache-module-caffeine-3.5.7.0.jar:cn/herodotus/engine/cache/caffeine/enhance/CaffeineNeverExpire.class */
public class CaffeineNeverExpire<K, V> implements Expiry<K, V> {
    public long expireAfterCreate(K key, V value, long currentTime) {
        return Long.MAX_VALUE;
    }

    public long expireAfterUpdate(K key, V value, long currentTime, long currentDuration) {
        return currentDuration;
    }

    public long expireAfterRead(K key, V value, long currentTime, long currentDuration) {
        return currentDuration;
    }
}
