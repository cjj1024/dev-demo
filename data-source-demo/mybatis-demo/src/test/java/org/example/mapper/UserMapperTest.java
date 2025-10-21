package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.MybatisTestApplication;
import org.example.repository.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = MybatisTestApplication.class)
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    DataSource dataSource;

    @Test
    public void testUserMapper() {

        log.info(dataSource.getClass().getSimpleName());

        User user = userMapper.selectByPrimaryKey(1L);
        log.info(String.valueOf(user.getUsername()));

    }
}