package com.example.app.cli.command;

import com.example.app.common.domain.User;
import com.example.app.common.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserCommandTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserCommand userCommand;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @Test
    void execute_prints_user_when_found() {
        User user = new User();
        user.setId("A001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        when(userService.findById("A001")).thenReturn(user);

        userCommand.execute(new String[]{"A001"});

        assertThat(out.toString()).contains("A001");
    }

    @Test
    void execute_prints_not_found_message_when_user_missing() {
        when(userService.findById("X999")).thenReturn(null);

        userCommand.execute(new String[]{"X999"});

        assertThat(out.toString()).contains("not found");
    }
}
