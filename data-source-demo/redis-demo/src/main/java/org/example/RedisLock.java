package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void runWithLock(String key, long timeoutSec, Runnable task, int retryTimes, long waitMillis) {
        String lockToken = null;

        for (int i = 0; i < retryTimes; i++) {
            lockToken = tryLock(key, timeoutSec);
            if (lockToken != null) {
                try {
                    task.run();
                } finally {
                    unlock(key, lockToken);
                }
            } else {
                try {
                    Thread.sleep(waitMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        throw new RuntimeException("get lock failed");
    }

    public String lock(String key, long timeoutSec) {
        String token = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, token, timeoutSec, TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(success)) {
            throw new RuntimeException("get lock fail");
        }
        return token;
    }

    public String tryLock(String key, long timeoutSec) {
        String token = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, token, timeoutSec, TimeUnit.SECONDS);

        return Boolean.TRUE.equals(success) ? token : null;
    }

    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else return 0 end";
    public boolean unlock(String key, String token) {
        if (key == null || token == null) return true;
        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class),
                Collections.singletonList(key),
                token
        );
        return result != null && result > 0;
    }
}
