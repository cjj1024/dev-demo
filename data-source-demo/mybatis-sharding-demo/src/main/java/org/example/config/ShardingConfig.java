package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShardingConfig {
    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        Properties props = new Properties();
        props.setProperty("sql-show", "true");
        props.setProperty("sql-simple", "false");
        return ShardingSphereDataSourceFactory.createDataSource(
                createDataSourceMap(),
                Collections.singleton(createShardingRuleConfiguration()),
                props
        );
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        HikariDataSource ds0 = new HikariDataSource();
        ds0.setJdbcUrl("jdbc:mysql://localhost:3306/testdb_0");
        ds0.setUsername("root");
        ds0.setPassword("123456");
        dataSourceMap.put("testdb_0", ds0);

        HikariDataSource ds1 = new HikariDataSource();
        ds1.setJdbcUrl("jdbc:mysql://localhost:3306/testdb_1");
        ds1.setUsername("root");
        ds1.setPassword("123456");
        dataSourceMap.put("testdb_1", ds1);


        return dataSourceMap;
    }

    private ShardingRuleConfiguration createShardingRuleConfiguration() {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 用户表分片规则
        ShardingTableRuleConfiguration userTableRuleConfig = new ShardingTableRuleConfiguration(
                "user", "testdb_${0..1}.user_${0..1}");

        // 分库策略
        Properties dbShardingProps = new Properties();
        dbShardingProps.setProperty("algorithm-expression", "testdb_${(user_id[-2..-1].toInteger() % 2)}");
        userTableRuleConfig.setDatabaseShardingStrategy(
                new StandardShardingStrategyConfiguration("user_id", "db-sharding-algorithm"));

        // 分表策略
        Properties tableShardingProps = new Properties();
        tableShardingProps.setProperty("algorithm-expression", "user_${(user_id[-4..-3].toInteger() % 2)}");
        userTableRuleConfig.setTableShardingStrategy(
                new StandardShardingStrategyConfiguration("user_id", "table-sharding-algorithm"));

        shardingRuleConfig.getTables().add(userTableRuleConfig);

        // 配置分片算法
        shardingRuleConfig.getShardingAlgorithms().put("db-sharding-algorithm",
                new AlgorithmConfiguration("INLINE", dbShardingProps));
        shardingRuleConfig.getShardingAlgorithms().put("table-sharding-algorithm",
                new AlgorithmConfiguration("INLINE", tableShardingProps));

        return shardingRuleConfig;
    }
}
