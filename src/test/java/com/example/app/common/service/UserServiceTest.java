package com.example.app.common.service;

import com.example.app.common.domain.User;
import com.example.app.primarydb.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_delegates_to_mapper_and_returns_user() {
        User expected = new User();
        expected.setId("A001");
        expected.setName("Alice");
        when(userMapper.findById("A001")).thenReturn(expected);

        User result = userService.findById("A001");

        assertThat(result).isEqualTo(expected);
        verify(userMapper).findById("A001");
    }

    @Test
    void findById_returns_null_when_mapper_finds_nothing() {
        when(userMapper.findById("X999")).thenReturn(null);

        assertThat(userService.findById("X999")).isNull();
        verify(userMapper).findById("X999");
    }
}
