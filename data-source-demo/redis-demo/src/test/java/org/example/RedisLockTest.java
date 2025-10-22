package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class RedisLockTest {
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testLock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);


        System.out.println(redisTemplate.opsForValue().get("test-lock:a"));
        System.out.println(redisTemplate.opsForValue().get("test-lock:b"));

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 200; j++) {
//                    String lock = redisLock.tryLock("redis-lock-test", 50);

                    try {
                        handle();
                    } finally {
//                        redisLock.unlock("redis-lock-test", lock);
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        System.out.println(redisTemplate.opsForValue().get("test-lock:a"));
        System.out.println(redisTemplate.opsForValue().get("test-lock:b"));
    }

    public void handle() {
        Integer a = (Integer) redisTemplate.opsForValue().get("test-lock:a");
        Integer b = (Integer) redisTemplate.opsForValue().get("test-lock:b");

        redisTemplate.opsForValue().set("test-lock:a", a + 1);
        redisTemplate.opsForValue().set("test-lock:b", b - 1);
    }
}
