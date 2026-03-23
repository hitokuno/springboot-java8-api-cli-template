package com.example.app.api.controller;

import com.example.app.common.domain.User;
import com.example.app.common.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void getUser_found_returns_200_with_body() throws Exception {
        User user = new User();
        user.setId("A001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        when(userService.findById("A001")).thenReturn(user);

        mvc.perform(get("/api/users/A001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("A001"))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));

        verify(userService).findById("A001");
    }

    @Test
    void getUser_notFound_returns_404() throws Exception {
        when(userService.findById("X999")).thenReturn(null);

        mvc.perform(get("/api/users/X999"))
                .andExpect(status().isNotFound());

        verify(userService).findById("X999");
    }
}
