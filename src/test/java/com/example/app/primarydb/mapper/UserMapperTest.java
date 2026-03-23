package com.example.app.primarydb.mapper;

import com.example.app.common.domain.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * MyBatis mapper test using H2 in-memory DB (Oracle compatibility mode).
 * Schema and data are loaded from src/test/resources/sql/.
 */
@MybatisTest
@Sql(scripts = {"/sql/schema.sql", "/sql/data.sql"})
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findById_returns_user_when_exists() {
        User user = userMapper.findById("A001");

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("A001");
        assertThat(user.getName()).isEqualTo("Alice");
        assertThat(user.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void findById_returns_null_when_not_found() {
        User user = userMapper.findById("X999");

        assertThat(user).isNull();
    }
}
