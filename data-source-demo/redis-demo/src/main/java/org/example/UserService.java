package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static final String USER_PREFIX = "user:";

    public void saveUser(User user) {
        String key = USER_PREFIX + user.getId();
        redisTemplate.opsForValue().set(key, user, 10, TimeUnit.MINUTES);
    }

    public User getUser(String id) {
        return (User) redisTemplate.opsForValue().get(USER_PREFIX + id);
    }

    public void deleteUser(String id) {
        redisTemplate.delete(USER_PREFIX + id);
    }

}
