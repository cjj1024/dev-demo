package org.example;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/redis-demo")
public class RedisDemoController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    RedisLock redisLock;
    @Autowired
    RedissonClient redissonClient;

    @PostMapping
    public String saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return "Saved!";
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "Deleted!";
    }

    @PostMapping("test-lock-init")
    public void testLockInit() {
        redisTemplate.opsForValue().set("test-lock:a", String.valueOf(0));
        redisTemplate.opsForValue().set("test-lock:b", String.valueOf(0));
    }

    @PostMapping("test-lock")
    public void testLock() throws InterruptedException {
        String lock = null;
        for (int i = 0; i < 5; i++) {
            try {
                lock = redisLock.lock("test-lock-key", 5);
            } catch (Exception e) {
                Thread.sleep(100);
            }
            if (lock != null) {
                break;
            }
        }

        if (lock == null) {
            return;
        }

        try {
            Integer a = Integer.parseInt(redisTemplate.opsForValue().get("test-lock:a"));
            Integer b = Integer.parseInt(redisTemplate.opsForValue().get("test-lock:b"));

            redisTemplate.opsForValue().set("test-lock:a", String.valueOf(a + 1));
            redisTemplate.opsForValue().set("test-lock:b", String.valueOf(b + 1));
        } finally {
            redisLock.unlock("test-lock-key", lock);
        }
    }

    @PostMapping("test-lock2")
    public void testLock2() throws InterruptedException {
        redisLock.runWithLock(
                "test-lock-key",
                5,
                () -> {
                    try {
                        Integer a = Integer.parseInt(redisTemplate.opsForValue().get("test-lock:a"));
                        Integer b = Integer.parseInt(redisTemplate.opsForValue().get("test-lock:b"));

                        redisTemplate.opsForValue().set("test-lock:a", String.valueOf(a + 1));
                        redisTemplate.opsForValue().set("test-lock:b", String.valueOf(b + 1));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                },
                5,
                100);
    }

    @PostMapping("test-lock3")
    public void testLock3() {
        RLock lock = redissonClient.getLock("test-lock-key");
        boolean locked = false;
        try {
            locked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (locked) {
                Integer a = Integer.parseInt(redisTemplate.opsForValue().get("test-lock:a"));
                Integer b = Integer.parseInt(redisTemplate.opsForValue().get("test-lock:b"));

                redisTemplate.opsForValue().set("test-lock:a", String.valueOf(a + 1));
                redisTemplate.opsForValue().set("test-lock:b", String.valueOf(b + 1));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

}
