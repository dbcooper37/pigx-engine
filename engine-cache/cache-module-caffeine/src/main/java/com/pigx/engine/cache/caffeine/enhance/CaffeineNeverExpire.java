package com.pigx.engine.cache.caffeine.enhance;

import com.github.benmanes.caffeine.cache.Expiry;


public class CaffeineNeverExpire<K, V> implements Expiry<K, V> {

    @Override
    public long expireAfterCreate(K key, V value, long currentTime) {
        return Long.MAX_VALUE;
    }

    @Override
    public long expireAfterUpdate(K key, V value, long currentTime, long currentDuration) {
        return currentDuration;
    }

    @Override
    public long expireAfterRead(K key, V value, long currentTime, long currentDuration) {
        return currentDuration;
    }
}
