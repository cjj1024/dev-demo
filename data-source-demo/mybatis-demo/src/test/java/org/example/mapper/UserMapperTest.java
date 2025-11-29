package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.MybatisTestApplication;
import org.example.repository.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest(classes = MybatisTestApplication.class)
public class UserMapperTest {
    @Autowired
    UserPOMapper userMapper;
    @Autowired
    DataSource dataSource;

    @Test
    public void testUserMapper() {

        log.info(dataSource.getClass().getSimpleName());

        UserPO user = userMapper.selectByPrimaryKey(1L);
        log.info(String.valueOf(user.getUsername()));

    }
}