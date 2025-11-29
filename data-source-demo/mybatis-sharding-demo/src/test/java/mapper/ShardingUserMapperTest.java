package mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.config.ShardingApplication;
import org.example.mapper.UserPOMapper;
import org.example.repository.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest(classes = ShardingApplication.class)
public class ShardingUserMapperTest {
    @Autowired
    UserPOMapper userMapper;
    @Autowired
    DataSource dataSource;

    @Test
    public void testUserMapper() {
        log.info(dataSource.getClass().getSimpleName());

        for (int i = 0; i < 10; i++) {
            UserPO user1 = new UserPO();
            user1.setUserId(String.valueOf(10000000 + RandomUtils.nextInt()));
            user1.setUsername(RandomStringUtils.randomAlphabetic(6));

            userMapper.insert(user1);
        }
    }
}