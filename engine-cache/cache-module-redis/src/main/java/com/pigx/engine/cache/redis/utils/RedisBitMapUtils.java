package com.pigx.engine.redis.utils;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
/* loaded from: cache-module-redis-3.5.7.0.jar:cn/herodotus/engine/cache/redis/utils/RedisBitMapUtils.class */
public class RedisBitMapUtils {
    private static StringRedisTemplate stringRedisTemplate;

    private static long hash(String key) {
        return Math.abs(Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(StandardCharsets.UTF_8)).asInt());
    }

    public static Boolean setBit(String key, String param, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, hash(param), value);
    }

    public static boolean getBit(String key, String param) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getBit(key, hash(param)));
    }

    public static Boolean setBit(String key, Long offset, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, offset.longValue(), value);
    }

    public static Boolean getBit(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    public static Long bitCount(String key) {
        return (Long) stringRedisTemplate.execute(connection -> {
            return connection.stringCommands().bitCount(key.getBytes(StandardCharsets.UTF_8));
        });
    }

    public static Long bitCount(String key, int start, int end) {
        return (Long) stringRedisTemplate.execute(connection -> {
            return connection.stringCommands().bitCount(key.getBytes(), start, end);
        });
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [byte[], byte[][]] */
    public static Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... destKey) {
        ?? r0 = new byte[destKey.length];
        for (int i = 0; i < destKey.length; i++) {
            r0[i] = destKey[i].getBytes();
        }
        return (Long) stringRedisTemplate.execute(connection -> {
            return connection.stringCommands().bitOp(op, saveKey.getBytes(), r0);
        });
    }

    public static Long bitOpResult(RedisStringCommands.BitOperation op, String saveKey, String... destKey) {
        bitOp(op, saveKey, destKey);
        return bitCount(saveKey);
    }

    @Autowired
    @Qualifier("stringRedisTemplate")
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate2) {
        stringRedisTemplate = stringRedisTemplate2;
    }
}
